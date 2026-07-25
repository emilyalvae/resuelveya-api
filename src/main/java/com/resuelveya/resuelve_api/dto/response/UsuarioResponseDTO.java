package com.resuelveya.resuelve_api.dto.response;

import com.resuelveya.resuelve_api.entity.Rol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        Rol rol
) {
}
