package com.resuelveya.resuelve_api.business.api.dto.tecnico;

public record TecnicoResponseDto(

        Long id,

        String nombre,

        String email,

        String telefono,

        Integer aniosExperiencia,

        Double calificacionPromedio,

        Long especialidadId,

        String nombreEspecialidad
) {
}