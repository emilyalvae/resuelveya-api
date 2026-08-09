package com.resuelveya.resuelve_api.business.api.dto.response;

import com.resuelveya.resuelve_api.business.data.entity.Rol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        Rol rol
) {
}
