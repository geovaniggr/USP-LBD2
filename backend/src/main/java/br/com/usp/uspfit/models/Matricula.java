package br.com.usp.uspfit.models;

import java.time.LocalDate;

public record Matricula(
        Integer id,
        TipoPlano plano,
        LocalDate dataInicio,
        LocalDate dataFim
) {}

