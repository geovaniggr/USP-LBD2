package br.com.usp.uspfit.service;

import br.com.usp.uspfit.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;
import java.util.function.BiFunction;

@Service
public class ApplicationService {

    @Autowired
    private JdbcTemplate executor;

    private BiFunction<ResultSet, Integer, TreinoExercicio> mapperExercicio(){
        return (rs, i) -> {
            try {
                var exercicio = new TreinoExercicio(
                        rs.getString("exercicio_nome"),
                        rs.getString("exercicio_grupo"),
                        rs.getString("exercicio_carga"),
                        rs.getInt("exercicio_serie"),
                        rs.getInt("exercicio_repeticoes")
                );

                return exercicio;
            }
            catch(Exception e){
                throw new RuntimeException("");
            }
        };
    }

    private BiFunction<ResultSet, Integer, Bioimpendancia> mapperBio(){
        return (rs, i) -> {
            try {
                return new Bioimpendancia(
                    rs.getInt("cod_cliente"),
                    rs.getDouble("massa_magra"),
                    rs.getDouble("imc"),
                    rs.getDouble("gordura_corporal"),
                    rs.getDouble("gordura_visceral"),
                    rs.getDouble("agua_corporal"),
                    rs.getDate("data_avaliacao").toLocalDate()
                );
            } catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }

        };
    }

    public BasicClientDTO getBasicClient(Integer id){

        var sql = """
            SELECT c.nome as cliente_nome,
                   c.email as cliente_email,
                   m.data_fim as matricula_fim
            FROM cliente c
            INNER JOIN matricula m on c.id = m.cliente_id AND m.data_fim = (SELECT MAX(data_fim) FROM matricula WHERE m.cliente_id = cliente_id)
            WHERE c.id = (?)
        """;

        var cliente = executor.queryForObject(
                sql,
                (rs, i) -> new BasicClientDTO(id, rs.getString("cliente_nome"), rs.getString("cliente_email"), rs.getDate("matricula_fim").toLocalDate()),
                new Object[]{id}
        );


        return cliente;
    }

    public HashMap<Integer, BasicInstructorDTO> getInstructorsAndHoursOfDay(){

        var result = new HashMap<Integer, BasicInstructorDTO>();

        var sql = """
            SELECT
                f.id as funcionario_id,
                f.nome as funcionario_nome,
                ap.horario as aula_horario
            FROM funcionario f
            INNER JOIN instrutor i on f.id = i.funcionario_id
            LEFT JOIN aula_personal ap on i.funcionario_id = ap.personal_id AND horario BETWEEN current_date + interval '9 hours' AND current_date + interval '23 hours'
            WHERE i.personal_ativo IS TRUE;
        """;

        executor.query(sql,(rs, i) -> {

            var id = rs.getInt("funcionario_id");

            var horarioPossivel = rs.getTimestamp("aula_horario");

            if(result.containsKey(id)){
                result.compute(id, (k, instructor) -> {

                    if(horarioPossivel != null){
                        instructor.addHorario(horarioPossivel.toLocalDateTime());
                    }

                    return instructor;
                });
            } else {
                var instructor = new BasicInstructorDTO(id, rs.getString("funcionario_nome"), new ArrayList<>());
                if(horarioPossivel != null){
                    instructor.addHorario(horarioPossivel.toLocalDateTime());
                }
                result.put(id, instructor);
            }


            return null;
        });


        return result;
    }

    public HashMap<Integer, Treino> getTrainingFromClient(Integer id){

        var result = new HashMap<Integer, Treino>();
        var sql = """
            SELECT t.data_inicio,
                   t.data_fim,
                   t.id as treino_id,
                   e.nome as exercicio_nome,
                   e.grupo_muscular as exercicio_grupo,
                   te.carga as exercicio_carga,
                   te.series as exercicio_serie,
                   te.repeticoes as exercicio_repeticoes
            FROM treino t
            INNER JOIN treino_exercicio te on t.id = te.treino_id
            INNER JOIN exercicio e on te.exercicio_id = e.id
            WHERE cliente_id = (?);
        """;

        executor.query(sql, (rs, i) -> {

            var treino = rs.getInt("treino_id");

            var exercicio = new TreinoExercicio(
                rs.getString("exercicio_nome"),
                rs.getString("exercicio_grupo"),
                rs.getString("exercicio_carga"),
                rs.getInt("exercicio_serie"),
                rs.getInt("exercicio_repeticoes")
            );

            if(result.containsKey(treino)){
                result.compute(treino, (k, t) -> {
                    t.addExercicio(exercicio);

                    return t;
                });
            } else {
                var created = new Treino(
                        treino,
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        new ArrayList<>()
                );

                created.addExercicio(exercicio);

                result.put(treino, created);
            }

            return null;
        }, new Object[]{id});

        return result;
    }

    public Treino getSingle(Integer treinoId){

        var sql = """
            SELECT t.id as treino_id,
                   t.data_inicio,
                   t.data_fim,
                   e.nome as exercicio_nome,
                   e.grupo_muscular as exercicio_grupo,
                   te.carga as exercicio_carga,
                   te.series as exercicio_serie,
                   te.repeticoes as exercicio_repeticoes
            FROM treino t
            INNER JOIN treino_exercicio te on t.id = te.treino_id
            INNER JOIN exercicio e on te.exercicio_id = e.id
            WHERE t.id = (?);
        """;

        var treino = new HashMap<Integer, Treino>();

        executor.query(sql, (rs, i) -> {

            var id = rs.getInt("treino_id");

            if(treino.containsKey(id)){
                treino.compute(id, (k, t) -> {

                    var exercicio = mapperExercicio().apply(rs, i);
                    t.addExercicio(exercicio);

                    return t;
                });
            } else {
                var created = new Treino(
                        id,
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        new ArrayList<>()
                );

                var exercicio = mapperExercicio().apply(rs, i);
                created.addExercicio(exercicio);

                treino.put(id, created);
            }

            return null;
        }, new Object[]{treinoId});

        return treino.values().stream().findFirst().orElse(null);
    }

    public List<Bioimpendancia> examsFromClient(Integer id){

        var sql = """
            SELECT *
            FROM biompendacia
            WHERE cod_cliente = (?);
        """;
        var result = new ArrayList<Bioimpendancia>();

        executor.query(
                sql,
                (rs, i) -> {
                    result.add(mapperBio().apply(rs, i));

                    return null;
                },
                new Object[]{ id }
        );

        return result;
    }

    public List<BasicExercise> getExercises(){
        var sql = """
            SELECT
                id,
                nome,
                grupo_muscular
            FROM exercicio;
        """;

        var result = new ArrayList<BasicExercise>();

        executor.query(sql, (rs, i) -> {
            var exercise = new BasicExercise(
              rs.getInt("id"),
              rs.getString("nome"),
              rs.getString("grupo_muscular")
            );

            result.add(exercise);

            return null;
        });

        return result;
    }

    public BasicClientDTO getClientByCPF(String cpf){
        var sql = """
            SELECT *
            FROM cliente
            WHERE cpf = (?)
        """;

        return executor.queryForObject(
                sql, (rs, i) -> new BasicClientDTO(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    null
                ),
                new Object[]{ cpf }
        );
    }

    public BasicClientDTO getClientByEmail(String email){
        var sql = """
            SELECT *
            FROM cliente
            WHERE email = (?)
        """;

        return executor.queryForObject(
                sql, (rs, i) -> new BasicClientDTO(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        null
                ),
                new Object[]{ email }
        );
    }



}
