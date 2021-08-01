package br.com.usp.uspfit;

import br.com.usp.uspfit.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class UspfitCLI implements CommandLineRunner {

    @Autowired
    ApplicationService service;

    @Override
    public void run(String... args) throws Exception {

//        var sql = """
//                    SELECT
//                        m.id as matricula_id,
//                        m.cliente_id,
//                        m.tipo_plano,
//                        m.data_inicio,
//                        m.data_fim,
//                        p.nome as plano_nome,
//                        p.valor as plano_valor,
//                        c.nome as cliente_nome,
//                        c.cpf as cliente_cpf,
//                        c.telefone as cliente_telefone,
//                        c.data_nascimento as cliente_nascimento,
//                        c.email as cliente_email,
//                        c.senha as cliente_senha
//                    FROM matricula m
//                    INNER JOIN plano p on m.tipo_plano = p.nome
//                    INNER JOIN cliente c on m.cliente_id = c.id
//                """;
//
//        var result = new HashMap<Integer, Cliente>();
//
//        executor.query(sql, (rs, i) -> {
//
//            var clienteId = rs.getInt("cliente_id");
//
//            if(result.containsKey(clienteId)){
//
//                var plano = new TipoPlano(rs.getString("plano_nome"), rs.getDouble("plano_valor"));
//
//                var matricula = new Matricula(
//                  rs.getInt("matricula_id"),
//                  plano,
//                  rs.getDate("data_inicio").toLocalDate(),
//                  rs.getDate("data_fim").toLocalDate()
//                );
//
//                result.compute(clienteId, (key, cliente) -> {
//                    cliente.addMatricula(matricula);
//                    return cliente;
//                });
////
//            } else {
//                var plano = new TipoPlano(rs.getString("plano_nome"), rs.getDouble("plano_valor"));
////                c.nome as cliente_nome,
////                        c.cpf as cliente_cpf,
////                c.telefone as cliente_telefone,
////                        c.data_nascimento as cliente_nascimento,
////                c.email as cliente_email,
////                        c.senha as cliente_senha
//                var cliente = new Cliente(
//                        clienteId,
//                        rs.getString("cliente_nome"),
//                        rs.getString("cliente_cpf"),
//                        rs.getString("cliente_telefone"),
//                        rs.getDate("cliente_nascimento").toLocalDate(),
//                        new ArrayList<Matricula>(),
//                        rs.getString("cliente_email"),
//                        rs.getString("cliente_senha")
//                );
//                var matricula = new Matricula(
//                        rs.getInt("matricula_id"),
//                        plano,
//                        rs.getDate("data_inicio").toLocalDate(),
//                        rs.getDate("data_fim").toLocalDate()
//                );
//
//                cliente.addMatricula(matricula);
//
//                result.put(clienteId, cliente);
//            }
//
//            return null;
//        });
//
//        result.forEach( (k, v) -> {
//            System.out.println("O usuario de ID: %d, com nome %s tem %d matriculas".formatted(k, v.nome(), v.matriculas().size()));
//        });

//        var cliente = service.getTrainingFromClient(4);
//
//        cliente.forEach( (id, t) -> {
//            System.out.println("Treino de id %d com %d exercicios".formatted(id, t.exercicios().size()));
//        });

//        var x = service.examsFromClient(4);

//        System.out.println(x);
    }
}
