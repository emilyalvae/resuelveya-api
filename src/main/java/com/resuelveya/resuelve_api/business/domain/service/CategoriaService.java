package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaResponseDto;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDto> listarTodas();

    CategoriaResponseDto obtenerPorId(Long id);

    CategoriaResponseDto crear(CategoriaRequestDto requestDto);

    CategoriaResponseDto actualizar(Long id, CategoriaRequestDto requestDto);

    void eliminar(Long id);
}
