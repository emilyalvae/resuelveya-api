package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.usuario.ActualizarPerfilRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.CambiarPasswordRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.PerfilResponseDto;
import com.resuelveya.resuelve_api.business.domain.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/me")
    public ResponseEntity<PerfilResponseDto> obtenerMiPerfil(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(perfilService.obtenerMiPerfil(email));
    }

    @PutMapping("/me")
    public ResponseEntity<PerfilResponseDto> actualizarMiPerfil(
            Authentication authentication,
            @Valid @RequestBody ActualizarPerfilRequestDto requestDto
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(perfilService.actualizarMiPerfil(email, requestDto));
    }

    @PutMapping("/cambiar-password")
    public ResponseEntity<Void> cambiarPassword(
            Authentication authentication,
            @Valid @RequestBody CambiarPasswordRequestDto requestDto
    ) {
        String email = authentication.getName();
        perfilService.cambiarPassword(email, requestDto);
        return ResponseEntity.noContent().build();
    }
}
