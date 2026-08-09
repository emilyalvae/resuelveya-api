
package com.resuelveya.resuelve_api.business.api.dto.usuario;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        Rol rol
) {
}
