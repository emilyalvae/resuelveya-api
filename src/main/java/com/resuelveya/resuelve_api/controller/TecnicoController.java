package com.resuelveya.resuelve_api.controller;

import com.resuelveya.resuelve_api.dto.request.TecnicoRequestDto;
import com.resuelveya.resuelve_api.dto.response.TecnicoResponseDto;
import com.resuelveya.resuelve_api.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;


    public TecnicoController(
            TecnicoService tecnicoService
    ) {
        this.tecnicoService = tecnicoService;
    }


    @GetMapping
    public ResponseEntity<List<TecnicoResponseDto>> obtenerTodos() {

        return ResponseEntity.ok(
                tecnicoService.obtenerTodos()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponseDto> obtenerPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tecnicoService.obtenerPorId(id)
        );
    }


    @PostMapping
    public ResponseEntity<TecnicoResponseDto> crear(
            @Valid @RequestBody TecnicoRequestDto request
    ) {

        TecnicoResponseDto tecnicoCreado =
                tecnicoService.crear(request);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tecnicoCreado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TecnicoResponseDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TecnicoRequestDto request
    ) {

        return ResponseEntity.ok(
                tecnicoService.actualizar(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        tecnicoService.eliminar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}