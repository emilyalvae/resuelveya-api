package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoResponseDto;
import com.resuelveya.resuelve_api.business.domain.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/email/{email}")
    public ResponseEntity<TecnicoResponseDto> buscarPorEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(
                tecnicoService.buscarPorEmail(email)
        );
    }

    @GetMapping("/consulta")
    public ResponseEntity<Page<TecnicoResponseDto>> consultar(
            @RequestParam(required = false) Long especialidadId,
            @RequestParam(required = false) String nombre,
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "nombre"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                tecnicoService.consultar(
                        especialidadId,
                        nombre,
                        pageable
                )
        );
    }
}