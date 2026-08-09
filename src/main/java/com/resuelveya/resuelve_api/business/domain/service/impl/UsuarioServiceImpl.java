package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.api.exception.RecursoDuplicadoException;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.api.exception.RolInvalidoException;
import com.resuelveya.resuelve_api.business.domain.mapper.UsuarioMapper;
import com.resuelveya.resuelve_api.business.data.repository.ClienteRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
import com.resuelveya.resuelve_api.business.domain.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                           TecnicoRepository tecnicoRepository,
                              ClienteRepository clienteRepository,
                              UsuarioMapper usuarioMapper){
     this.usuarioRepository=usuarioRepository;
     this.tecnicoRepository=tecnicoRepository;
     this.clienteRepository=clienteRepository;
     this.usuarioMapper=usuarioMapper;

    }

    @Override
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDto)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Usuario no encontrado con ID:"+id));
        return usuarioMapper.toResponseDto(usuario);
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO request){
        if (usuarioRepository.existsByEmailIgnoreCase(request.email())) {
            throw new RecursoDuplicadoException("El email ya está registrado");
        }

        Usuario usuario;
        switch (request.rol()) {
            case CLIENTE -> usuario = clienteRepository.save(usuarioMapper.toCliente(request));
            case TECNICO -> usuario = tecnicoRepository.save(usuarioMapper.toTecnico(request));
            case ADMIN   -> usuario = usuarioRepository.save(usuarioMapper.toAdmin(request));
            default      -> throw new RolInvalidoException("Rol inválido");
        }

        return usuarioMapper.toResponseDto(usuario);
 }
    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        usuarioMapper.actualizarEntidad(request, usuario);

        Usuario actualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioResponseDTO> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(usuarioMapper::toResponseDto)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        return usuarioMapper.toResponseDto(usuario);
    }

    @Override
    public Page<UsuarioResponseDTO> consultar(String nombre, Pageable pageable) {
        String nombreNormalizado =
                nombre == null || nombre.isBlank()
                        ? null
                        : nombre.trim();

        return usuarioRepository.buscarUsuarios(
                        nombreNormalizado,
                        pageable
                )
                .map(usuarioMapper::toResponseDto);
    }

}
