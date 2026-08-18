package com.resuelveya.resuelve_api.business.api.dto.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServicioResponseDto(
        Long id,
        String titulo,
        String descripcion,
        BigDecimal precioEstimado,
        String tiempoEstimado,
        Boolean activo,
        Long categoriaId,
        String categoriaNombre,
        Long tecnicoId,
        String tecnicoNombre,
        LocalDateTime createdAt
) {
}
