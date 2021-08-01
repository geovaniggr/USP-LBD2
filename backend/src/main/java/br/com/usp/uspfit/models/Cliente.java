package br.com.usp.uspfit.models;

import java.time.LocalDate;
import java.util.List;

public record Cliente(
        Integer id,
        String nome,
        String cpf,
        String telefone,
        LocalDate dataNascimento,
        List<Matricula> matriculas,
        String email,
        String senha
) {
    public void addMatricula(Matricula matricula){
        matriculas.add(matricula);
    }
}
