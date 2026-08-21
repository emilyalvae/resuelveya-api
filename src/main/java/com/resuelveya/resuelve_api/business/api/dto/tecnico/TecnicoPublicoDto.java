package com.resuelveya.resuelve_api.business.api.dto.tecnico;

public record TecnicoPublicoDto(
        Long id,
        String nombre,
        String fotoUrl,
        String ciudad,
        String codigoUbigeo,
        String presentacion,
        Integer aniosExperiencia,
        Boolean validacion,
        Double calificacionPromedio,
        Long totalResenias,
        Long especialidadId,
        String especialidadNombre
) {
}
