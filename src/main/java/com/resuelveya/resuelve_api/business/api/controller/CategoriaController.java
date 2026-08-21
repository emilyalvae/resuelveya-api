package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaResponseDto;
import com.resuelveya.resuelve_api.business.domain.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/api/v1/categorias", "/api/especialidades" })
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> crear(@Valid @RequestBody CategoriaRequestDto requestDto) {
        CategoriaResponseDto nueva = categoriaService.crear(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDto requestDto) {
        return ResponseEntity.ok(categoriaService.actualizar(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}