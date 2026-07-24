package com.resuelveya.resuelve_api.controller;

import com.resuelveya.resuelve_api.model.Usuario;
import com.resuelveya.resuelve_api.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

    @GetMapping
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario registrar(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }


}
