package com.resuelveya.resuelve_api.controller;

import com.resuelveya.resuelve_api.dto.UsuarioRequest;
import com.resuelveya.resuelve_api.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@GetMapping("/usuarios")
public class UsuarioViewController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioViewController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios"; // busca usuarios.html en templates/
    }

    @GetMapping("/nuevo")
    public String registrarUsuarios(Model model) {
        model.addAttribute("usuarioForm",new UsuarioRequest)
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios"; // busca usuarios.html en templates/
    }
}
