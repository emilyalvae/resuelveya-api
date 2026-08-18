package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.usuario.ActualizarPerfilRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.PerfilResponseDto;

public interface PerfilService {

    PerfilResponseDto obtenerMiPerfil(String email);

    PerfilResponseDto actualizarMiPerfil(String email, ActualizarPerfilRequestDto requestDto);
}
