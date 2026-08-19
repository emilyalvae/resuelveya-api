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
        Double tecnicoCalificacionPromedio
) {
}
