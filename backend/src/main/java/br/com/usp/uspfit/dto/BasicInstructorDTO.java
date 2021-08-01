package br.com.usp.uspfit.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BasicInstructorDTO(
        Integer id,
        String nome,
        List<LocalDateTime> horarios
) {
    public void addHorario(LocalDateTime horario){
        horarios.add(horario);
    }
}
