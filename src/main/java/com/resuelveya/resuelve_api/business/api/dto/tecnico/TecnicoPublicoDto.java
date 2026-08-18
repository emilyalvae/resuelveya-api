package com.resuelveya.resuelve_api.business.api.dto.tecnico;

public record TecnicoPublicoDto(
        Long id,
        String nombre,
        String fotoUrl,
        String distrito,
        String ciudad,
        String presentacion,
        Integer aniosExperiencia,
        Double calificacionPromedio,
        Long totalResenias,
        Long especialidadId,
        String especialidadNombre
) {
}
