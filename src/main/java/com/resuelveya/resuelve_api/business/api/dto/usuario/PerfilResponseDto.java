package com.resuelveya.resuelve_api.business.api.dto.usuario;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;

public record PerfilResponseDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        String fotoUrl,
        String ciudad,
        String codigoUbigeo,
        Rol rol,
        // Campos Cliente
        String direccion,
        Double latitud,
        Double longitud,
        // Campos Técnico
        String presentacion,
        Integer aniosExperiencia,
        Double calificacionPromedio,
        String yapeNumero,
        String plinNumero,
        String titularPago,
        Long especialidadId,
        String especialidadNombre,
        Boolean validacion
) {
}
