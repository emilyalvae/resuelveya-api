package com.resuelveya.resuelve_api.service;

import com.resuelveya.resuelve_api.entity.Especialidad;
import com.resuelveya.resuelve_api.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public Especialidad crearEspecialidad(Especialidad especialidad) {
        // Configurar Flush para mejorar el rendimiento
        return especialidadRepository.saveAndFlush(especialidad);
    }

    @Transactional(readOnly = true)
    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Especialidad> obtenerPorId(Long id) {
        return especialidadRepository.findById(id);
    }

    @Transactional
    public Especialidad actualizarEspecialidad(Long id, Especialidad datosActualizados) {
        return especialidadRepository.findById(id).map(existente -> {
            existente.setNombre(datosActualizados.getNombre());
            existente.setDescripcion(datosActualizados.getDescripcion());
            return especialidadRepository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarEspecialidad(Long id) {
        if (especialidadRepository.existsById(id)) {
            especialidadRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar: Especialidad no encontrada");
        }
    }
}