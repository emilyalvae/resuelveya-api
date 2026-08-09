package com.resuelveya.resuelve_api.business.domain.service;

import com.resuelveya.resuelve_api.business.api.dto.request.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> obtenerTodos();
    UsuarioResponseDTO obtenerPorId(Long id);
    UsuarioResponseDTO crear(UsuarioRequestDTO request);
    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);
    void eliminar(Long id);
    List<UsuarioResponseDTO> buscarPorNombre(String nombre);
    UsuarioResponseDTO buscarPorEmail(String email);
}
