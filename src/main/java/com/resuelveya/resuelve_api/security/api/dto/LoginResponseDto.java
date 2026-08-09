package com.resuelveya.resuelve_api.security.api.dto;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;

public record LoginResponseDto(
        String token,
        String tipo,
        long expiresIn,
        String email,
        Rol rol
) {
}
