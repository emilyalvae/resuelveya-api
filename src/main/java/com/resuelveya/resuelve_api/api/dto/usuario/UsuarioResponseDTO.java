package com.resuelveya.resuelve_api.api.dto.usuario;

import com.resuelveya.resuelve_api.data.entity.enums.Rol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        Rol rol
) {
}
