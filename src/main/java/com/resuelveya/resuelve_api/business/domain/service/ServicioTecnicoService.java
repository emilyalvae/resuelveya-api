package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoPerfilRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.PerfilResponseDto;

import java.util.List;

public interface ServicioTecnicoService {

    PerfilResponseDto actualizarPerfilTecnico(String email, TecnicoPerfilRequestDto requestDto);

    List<ServicioResponseDto> listarMisServicios(String email);

    ServicioResponseDto obtenerMiServicioPorId(String email, Long servicioId);

    ServicioResponseDto crearServicio(String email, ServicioRequestDto requestDto);

    ServicioResponseDto actualizarServicio(String email, Long servicioId, ServicioRequestDto requestDto);

    void eliminarServicio(String email, Long servicioId);
}
