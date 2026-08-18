package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioPublicoDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoPublicoDto;
import com.resuelveya.resuelve_api.business.domain.service.CatalogoPublicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/public")
public class PublicCatalogoController {

    private final CatalogoPublicoService catalogoPublicoService;

    public PublicCatalogoController(CatalogoPublicoService catalogoPublicoService) {
        this.catalogoPublicoService = catalogoPublicoService;
    }

    @GetMapping("/servicios")
    public ResponseEntity<Page<ServicioPublicoDto>> buscarServicios(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String distrito,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ServicioPublicoDto> servicios = catalogoPublicoService.buscarServicios(
                q,
                categoriaId,
                distrito,
                precioMin,
                precioMax,
                pageable
        );
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/tecnicos")
    public ResponseEntity<Page<TecnicoPublicoDto>> listarTecnicos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String nombre,
            @PageableDefault(page = 0, size = 10, sort = "calificacionPromedio", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TecnicoPublicoDto> tecnicos = catalogoPublicoService.listarTecnicosPublicos(
                categoriaId,
                nombre,
                pageable
        );
        return ResponseEntity.ok(tecnicos);
    }
}
