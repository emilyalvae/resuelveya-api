package com.resuelveya.resuelve_api.service;

import com.resuelveya.resuelve_api.dto.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.model.Usuario;
import com.resuelveya.resuelve_api.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final EntityManager entityManager;

 public UsuarioService(UsuarioRepository usuarioRepository,
                       TecnicoRepository tecnicoRepository, EntityManager entityManager){
     this.usuarioRepository=usuarioRepository;
     this.tecnicoRepository=tecnicoRepository;
     this.entityManager=entityManager;

 }

 @Transactional
    public Usuario registrarUsuario(UsuarioRequestDTO dto){
     Usuario usuario = new Usuario(
             null,
             dto.getNombre(),
             dto.getEmail(),
             dto.getTelefono()
     );

    entityManager.persist(usuario);

    entityManager.flush();

     //if para segun rol
     return usuarioGuardado;
 }

}
