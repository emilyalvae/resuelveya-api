package com.resuelveya.resuelve_api.business.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarPerfilRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 80, message = "El nombre no debe superar los 80 caracteres")
        String nombre,

        @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
        String telefono,

        String fotoUrl,

        @Size(max = 200, message = "La dirección no debe superar los 200 caracteres")
        String direccion,

        @Size(max = 100, message = "El distrito no debe superar los 100 caracteres")
        String distrito,

        @Size(max = 100, message = "La ciudad no debe superar los 100 caracteres")
        String ciudad
) {
}
