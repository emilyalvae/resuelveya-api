package com.resuelveya.resuelve_api.domain.service;

import com.resuelveya.resuelve_api.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.api.dto.usuario.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> obtenerTodos();
    UsuarioResponseDTO obtenerPorId(Long id);
    UsuarioResponseDTO crear(UsuarioRequestDTO request);
    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request);
    void eliminar(Long id);
    List<UsuarioResponseDTO> buscarPorNombre(String nombre);
    UsuarioResponseDTO buscarPorEmail(String email);

    Page<UsuarioResponseDTO> consultar(String nombre,
                                       Pageable pageable);
}
