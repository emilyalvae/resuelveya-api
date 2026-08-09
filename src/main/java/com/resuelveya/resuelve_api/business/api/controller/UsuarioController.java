package com.resuelveya.resuelve_api.business.api.controller;

import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.business.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService usuarioService){

        this.usuarioService=usuarioService;
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        // Log de acceso al endpoint
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("=== GET /api/v1/usuarios ===");
        logger.info("Usuario autenticado: {}", auth != null ? auth.getName() : "NO AUTENTICADO");
        logger.info("Autoridades: {}", auth != null ? auth.getAuthorities() : "NINGUNA");
        logger.info("IsAuthenticated: {}", auth != null ? auth.isAuthenticated() : false);
        
        if (auth != null && auth.isAuthenticated()) {
            logger.info("✅ ACCESO PERMITIDO");
        } else {
            logger.warn("❌ ACCESO DENEGADO - No autenticado");
        }
        
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO request) {

        UsuarioResponseDTO creado = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id, @Valid
                                                         @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    // Buscar por email
    @GetMapping("/buscar/email")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    @GetMapping("/consulta")
    public ResponseEntity<Page<UsuarioResponseDTO>>consultar(
            @RequestParam(required = false) String nombre,
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "nombre"
            )
            Pageable pageable
    ){
        return ResponseEntity.ok(usuarioService.consultar(nombre, pageable));
    }

}
