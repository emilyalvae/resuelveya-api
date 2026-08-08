package com.resuelveya.resuelve_api.dto.response;

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