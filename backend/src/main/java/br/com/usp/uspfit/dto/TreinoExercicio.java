package br.com.usp.uspfit.dto;

public record TreinoExercicio(
        String nome,
        String grupoMuscular,
        String carga,
        Integer series,
        Integer repeticoes
) {
}
