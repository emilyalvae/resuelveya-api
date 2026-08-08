package com.resuelveya.resuelve_api.service;

import com.resuelveya.resuelve_api.dto.request.TecnicoRequestDto;
import com.resuelveya.resuelve_api.dto.response.TecnicoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TecnicoService {

    List<TecnicoResponseDto> obtenerTodos();

    TecnicoResponseDto obtenerPorId(Long id);

    TecnicoResponseDto crear(TecnicoRequestDto requestDto);

    TecnicoResponseDto actualizar(Long id, TecnicoRequestDto requestDto);

    void eliminar(Long id);

    // Búsqueda por filtro único (equivalente a buscarPorNumeroDocumento)
    TecnicoResponseDto buscarPorEmail(String email);

    // Búsqueda paginada con filtros opcionales
    Page<TecnicoResponseDto> consultar(
            Long especialidadId,
            String nombre,
            Pageable pageable
    );
}