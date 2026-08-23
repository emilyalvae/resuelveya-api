package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDto> listarTodas();

    Page<CategoriaResponseDto> listarPaginado(String nombre, Pageable pageable);

    CategoriaResponseDto obtenerPorId(Long id);

    CategoriaResponseDto crear(CategoriaRequestDto requestDto);

    CategoriaResponseDto actualizar(Long id, CategoriaRequestDto requestDto);

    void eliminar(Long id);
}
