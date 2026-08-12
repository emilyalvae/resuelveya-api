package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.cliente.ClienteRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.cliente.ClienteResponseDto;

import java.util.List;

public interface ClienteService {

    List<ClienteResponseDto> obtenerTodos();

    ClienteResponseDto obtenerPorId(Long id);

    ClienteResponseDto crear(ClienteRequestDto requestDto);

    ClienteResponseDto actualizar(Long id, ClienteRequestDto requestDto);

    void eliminar(Long id);

    void procesamientoMasivoClientes(List<ClienteRequestDto> listaClientes);
}