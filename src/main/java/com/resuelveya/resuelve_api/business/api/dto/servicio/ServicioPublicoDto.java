package com.resuelveya.resuelve_api.business.api.dto.servicio;

import java.math.BigDecimal;

public record ServicioPublicoDto(
        Long id,
        String titulo,
        String descripcion,
        BigDecimal precioEstimado,
        String tiempoEstimado,
        Long categoriaId,
        String categoriaNombre,
        Long tecnicoId,
        String tecnicoNombre,
        String tecnicoFotoUrl,
        String tecnicoCiudad,
        String tecnicoCodigoUbigeo,
        Double calificacionPromedio,
        Long totalResenias,
        Double tecnicoCalificacionPromedio
) {
    public ServicioPublicoDto(
            Long id,
            String titulo,
            String descripcion,
            BigDecimal precioEstimado,
            String tiempoEstimado,
            Long categoriaId,
            String categoriaNombre,
            Long tecnicoId,
            String tecnicoNombre,
            String tecnicoFotoUrl,
            String tecnicoCiudad,
            String tecnicoCodigoUbigeo,
            Double calificacionPromedio,
            Long totalResenias
    ) {
        this(id, titulo, descripcion, precioEstimado, tiempoEstimado, categoriaId, categoriaNombre, tecnicoId, tecnicoNombre, tecnicoFotoUrl, tecnicoCiudad, tecnicoCodigoUbigeo, calificacionPromedio, totalResenias, calificacionPromedio);
    }
}
