package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioPublicoDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoCompletoResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoPublicoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface CatalogoPublicoService {

    Page<ServicioPublicoDto> buscarServicios(
            String query,
            Long categoriaId,
            String distrito,
            BigDecimal precioMin,
            BigDecimal precioMax,
            Pageable pageable
    );

    Page<TecnicoPublicoDto> listarTecnicosPublicos(
            Long categoriaId,
            String nombre,
            Pageable pageable
    );

    TecnicoCompletoResponseDto obtenerTecnicoCompleto(Long tecnicoId);
}
