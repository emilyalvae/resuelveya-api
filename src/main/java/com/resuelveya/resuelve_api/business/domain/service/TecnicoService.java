package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TecnicoService {

    List<TecnicoResponseDto> obtenerTodos();

    TecnicoResponseDto obtenerPorId(Long id);

    TecnicoResponseDto crear(TecnicoRequestDto requestDto);

    TecnicoResponseDto actualizar(Long id, TecnicoRequestDto requestDto);

    void eliminar(Long id);

    TecnicoResponseDto buscarPorEmail(String email);

    Page<TecnicoResponseDto> consultar(
            Long especialidadId,
            String nombre,
            Pageable pageable
    );
}