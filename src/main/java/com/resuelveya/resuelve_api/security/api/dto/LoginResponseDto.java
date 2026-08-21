package com.resuelveya.resuelve_api.security.api.dto;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;

public record LoginResponseDto(
        String token,
        String tipo,
        long expiresIn,
        String email,
        Rol rol,
        Long id,
        String nombre,
        String telefono
) {
    public LoginResponseDto(String token, String tipo, long expiresIn, String email, Rol rol) {
        this(token, tipo, expiresIn, email, rol, null, null, null);
    }
}
