package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.usuario.ActualizarPerfilRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.PerfilResponseDto;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.repository.ClienteRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
import com.resuelveya.resuelve_api.business.domain.service.PerfilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;

    public PerfilServiceImpl(
            UsuarioRepository usuarioRepository,
            TecnicoRepository tecnicoRepository,
            ClienteRepository clienteRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PerfilResponseDto obtenerMiPerfil(String email) {
        Usuario usuario = buscarUsuarioPorEmail(email);
        return mapearAPerfilResponse(usuario);
    }

    @Override
    public PerfilResponseDto actualizarMiPerfil(String email, ActualizarPerfilRequestDto requestDto) {
        Usuario usuario = buscarUsuarioPorEmail(email);

        usuario.setNombre(requestDto.nombre().trim());
        if (requestDto.telefono() != null) {
            usuario.setTelefono(requestDto.telefono().trim());
        }
        if (requestDto.fotoUrl() != null) {
            usuario.setFotoUrl(requestDto.fotoUrl().trim());
        }
        if (requestDto.direccion() != null) {
            usuario.setDireccion(requestDto.direccion().trim());
        }
        if (requestDto.distrito() != null) {
            usuario.setDistrito(requestDto.distrito().trim());
        }
        if (requestDto.ciudad() != null) {
            usuario.setCiudad(requestDto.ciudad().trim());
        }

        Usuario actualizado = usuarioRepository.saveAndFlush(usuario);

        if (actualizado.getRol() != null) {
            switch (actualizado.getRol()) {
                case CLIENTE -> clienteRepository.registrarFilaClienteSiNoExiste(actualizado.getId());
                case TECNICO -> tecnicoRepository.registrarFilaTecnicoSiNoExiste(actualizado.getId());
                default -> {}
            }
        }

        return mapearAPerfilResponse(actualizado);
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + email));
    }

    private PerfilResponseDto mapearAPerfilResponse(Usuario usuario) {
        if (usuario instanceof Tecnico tecnico) {
            return new PerfilResponseDto(
                    tecnico.getId(),
                    tecnico.getNombre(),
                    tecnico.getEmail(),
                    tecnico.getTelefono(),
                    tecnico.getFotoUrl(),
                    tecnico.getDireccion(),
                    tecnico.getDistrito(),
                    tecnico.getCiudad(),
                    tecnico.getRol(),
                    tecnico.getPresentacion(),
                    tecnico.getAniosExperiencia(),
                    tecnico.getCalificacionPromedio(),
                    tecnico.getYapeNumero(),
                    tecnico.getPlinNumero(),
                    tecnico.getTitularPago(),
                    tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getId() : null,
                    tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getNombre() : null
            );
        }

        // Si es una instancia base de Usuario o Cliente, verificar si existe registro en tecnico
        return tecnicoRepository.findById(usuario.getId())
                .map(tecnico -> new PerfilResponseDto(
                        tecnico.getId(),
                        tecnico.getNombre(),
                        tecnico.getEmail(),
                        tecnico.getTelefono(),
                        tecnico.getFotoUrl(),
                        tecnico.getDireccion(),
                        tecnico.getDistrito(),
                        tecnico.getCiudad(),
                        tecnico.getRol(),
                        tecnico.getPresentacion(),
                        tecnico.getAniosExperiencia(),
                        tecnico.getCalificacionPromedio(),
                        tecnico.getYapeNumero(),
                        tecnico.getPlinNumero(),
                        tecnico.getTitularPago(),
                        tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getId() : null,
                        tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getNombre() : null
                ))
                .orElseGet(() -> new PerfilResponseDto(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getTelefono(),
                        usuario.getFotoUrl(),
                        usuario.getDireccion(),
                        usuario.getDistrito(),
                        usuario.getCiudad(),
                        usuario.getRol(),
                        null, null, null, null, null, null, null, null
                ));
    }
}
