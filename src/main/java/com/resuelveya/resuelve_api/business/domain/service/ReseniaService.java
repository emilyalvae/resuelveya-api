package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;

import java.util.List;

public interface ReseniaService {

    ReseniaResponseDto crearResenia(String emailCliente, ReseniaRequestDto requestDto);

    ReseniaResponseDto actualizarResenia(String emailCliente, Long reseniaId, ReseniaRequestDto requestDto);

    List<ReseniaResponseDto> listarReseniasPorServicio(Long servicioId);

    List<ReseniaResponseDto> listarReseniasPorTecnico(Long tecnicoId);
}

