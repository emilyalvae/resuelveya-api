package com.resuelveya.resuelve_api.business.api.dto.servicio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServicioRequestDto(
        @NotBlank(message = "El título del servicio es obligatorio")
        @Size(max = 120, message = "El título no debe superar los 120 caracteres")
        String titulo,

        @NotBlank(message = "La descripción del servicio es obligatoria")
        @Size(max = 1000, message = "La descripción no debe superar los 1000 caracteres")
        String descripcion,

        @NotNull(message = "El precio estimado es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio estimado debe ser mayor a 0")
        BigDecimal precioEstimado,

        @Size(max = 50, message = "El tiempo estimado no debe superar los 50 caracteres")
        String tiempoEstimado,

        @NotNull(message = "La categoría es obligatoria")
        Long categoriaId,

        Boolean activo
) {
}
