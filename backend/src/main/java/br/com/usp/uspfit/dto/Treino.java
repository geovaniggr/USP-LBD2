package br.com.usp.uspfit.dto;

import java.time.LocalDate;
import java.util.List;

public record Treino(Integer id, LocalDate dataInicio, LocalDate dataFim, List<TreinoExercicio> exercicios) {
    public void addExercicio(TreinoExercicio exercicio){
        exercicios.add(exercicio);
    }
}
