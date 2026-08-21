package com.resuelveya.resuelve_api.business.api.dto.tecnico;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record TecnicoPerfilRequestDto(
        @Size(max = 1000, message = "La presentación no debe superar los 1000 caracteres")
        String presentacion,

        @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
        Integer aniosExperiencia,

        Long especialidadId,

        @Size(max = 20, message = "El número de Yape no debe superar los 20 caracteres")
        String yapeNumero,

        @Size(max = 20, message = "El número de Plin no debe superar los 20 caracteres")
        String plinNumero,

        @Size(max = 120, message = "El titular de pago no debe superar los 120 caracteres")
        String titularPago,

        String fotoUrl
) {
}
