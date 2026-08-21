package com.resuelveya.resuelve_api.business.api.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDto(
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
        String descripcion
) {
}
