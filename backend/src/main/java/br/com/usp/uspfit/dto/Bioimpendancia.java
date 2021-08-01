package br.com.usp.uspfit.dto;

import java.time.LocalDate;

public record Bioimpendancia(
        Integer id,
        Double massaMagra,
        Double imc,
        Double gorduraCorporal,
        Double gorduraVisceral,
        Double aguaCorporal,
        LocalDate dataAvalicao
) {
}
