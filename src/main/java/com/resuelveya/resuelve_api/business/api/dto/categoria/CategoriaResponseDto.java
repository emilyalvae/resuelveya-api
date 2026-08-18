package com.resuelveya.resuelve_api.business.api.dto.categoria;

public record CategoriaResponseDto(
        Long id,
        String nombre,
        String descripcion,
        Integer totalTecnicos
) {
}
