package com.resuelveya.resuelve_api.business.api.dto.usuario;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;

public record PerfilResponseDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        String fotoUrl,
        String direccion,
        String distrito,
        String ciudad,
        Rol rol,
        String presentacion,
        Integer aniosExperiencia,
        Double calificacionPromedio,
        String yapeNumero,
        String plinNumero,
        String titularPago,
        Long especialidadId,
        String especialidadNombre
) {
}
