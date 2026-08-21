package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.domain.service.ReseniaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resenias")
public class ReseniaController {

    private final ReseniaService reseniaService;

    public ReseniaController(ReseniaService reseniaService) {
        this.reseniaService = reseniaService;
    }

    @PostMapping
    public ResponseEntity<ReseniaResponseDto> crearResenia(
            Authentication authentication,
            @Valid @RequestBody ReseniaRequestDto requestDto
    ) {
        String email = authentication.getName();
        ReseniaResponseDto creada = reseniaService.crearResenia(email, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReseniaResponseDto> actualizarResenia(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody ReseniaRequestDto requestDto
    ) {
        String email = authentication.getName();
        ReseniaResponseDto actualizada = reseniaService.actualizarResenia(email, id, requestDto);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/servicio/{servicioId}")
    public ResponseEntity<List<ReseniaResponseDto>> listarReseniasPorServicio(@PathVariable Long servicioId) {
        return ResponseEntity.ok(reseniaService.listarReseniasPorServicio(servicioId));
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<ReseniaResponseDto>> listarReseniasPorTecnico(@PathVariable Long tecnicoId) {
        return ResponseEntity.ok(reseniaService.listarReseniasPorTecnico(tecnicoId));
    }
}

