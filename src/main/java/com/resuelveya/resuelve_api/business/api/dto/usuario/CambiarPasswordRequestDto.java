package com.resuelveya.resuelve_api.business.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CambiarPasswordRequestDto(
        @NotBlank(message = "La contraseña actual es obligatoria")
        String passwordActual,

        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 6, message = "La nueva contraseña debe tener al menos 6 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
                message = "La nueva contraseña debe tener al menos una letra mayúscula, una minúscula y un número"
        )
        String nuevaPassword,

        @NotBlank(message = "Debe confirmar la nueva contraseña")
        String confirmarPassword
) {
}
