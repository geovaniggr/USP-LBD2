package br.com.usp.uspfit.dto;

import java.time.LocalDate;

public record BasicClientDTO(Integer id, String nome, String email, LocalDate finalMatricula) {}
