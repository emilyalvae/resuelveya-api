package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoPerfilRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.usuario.PerfilResponseDto;
import com.resuelveya.resuelve_api.business.domain.service.ServicioTecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tecnico")
public class TecnicoGestionController {

    private final ServicioTecnicoService servicioTecnicoService;

    public TecnicoGestionController(ServicioTecnicoService servicioTecnicoService) {
        this.servicioTecnicoService = servicioTecnicoService;
    }

    @PutMapping("/perfil")
    public ResponseEntity<PerfilResponseDto> actualizarPerfil(
            Authentication authentication,
            @Valid @RequestBody TecnicoPerfilRequestDto requestDto
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(servicioTecnicoService.actualizarPerfilTecnico(email, requestDto));
    }

    @GetMapping("/servicios")
    public ResponseEntity<List<ServicioResponseDto>> listarMisServicios(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(servicioTecnicoService.listarMisServicios(email));
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<ServicioResponseDto> obtenerMiServicio(
            Authentication authentication,
            @PathVariable Long id
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(servicioTecnicoService.obtenerMiServicioPorId(email, id));
    }

    @PostMapping("/servicios")
    public ResponseEntity<ServicioResponseDto> crearServicio(
            Authentication authentication,
            @Valid @RequestBody ServicioRequestDto requestDto
    ) {
        String email = authentication.getName();
        ServicioResponseDto creado = servicioTecnicoService.crearServicio(email, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/servicios/{id}")
    public ResponseEntity<ServicioResponseDto> actualizarServicio(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody ServicioRequestDto requestDto
    ) {
        String email = authentication.getName();
        return ResponseEntity.ok(servicioTecnicoService.actualizarServicio(email, id, requestDto));
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> eliminarServicio(
            Authentication authentication,
            @PathVariable Long id
    ) {
        String email = authentication.getName();
        servicioTecnicoService.eliminarServicio(email, id);
        return ResponseEntity.noContent().build();
    }
}
