package com.resuelveya.resuelve_api.controller;

import com.resuelveya.resuelve_api.entity.Tecnico;
import com.resuelveya.resuelve_api.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping
    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> obtenerPorId(@PathVariable Long id) {
        return tecnicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tecnico> crear(@RequestBody Tecnico tecnico) {
        Tecnico nuevo = tecnicoRepository.save(tecnico);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PostMapping("/lote")
    public ResponseEntity<String> registrarLote(@RequestBody List<Tecnico> tecnicos) {
        tecnicoService.registrarTecnicosEnLote(tecnicos);
        return ResponseEntity.ok("Proceso en lote ejecutado con flush() correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> actualizar(@PathVariable Long id, @RequestBody Tecnico detalles) {
        return tecnicoRepository.findById(id).map(tec -> {
            tec.setNombre(detalles.getNombre());
            tec.setEmail(detalles.getEmail());
            tec.setTelefono(detalles.getTelefono());
            tec.setAniosExperiencia(detalles.getAniosExperiencia());
            tec.setCalificacionPromedio(detalles.getCalificacionPromedio());
            tec.setEspecialidad(detalles.getEspecialidad());
            Tecnico actualizado = tecnicoRepository.save(tec);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (tecnicoRepository.existsById(id)) {
            tecnicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}