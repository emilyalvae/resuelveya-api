package com.resuelveya.resuelve_api.service;

import com.resuelveya.resuelve_api.dto.request.TecnicoRequestDto;
import com.resuelveya.resuelve_api.dto.response.TecnicoResponseDto;
import com.resuelveya.resuelve_api.entity.Especialidad;
import com.resuelveya.resuelve_api.entity.Tecnico;
import com.resuelveya.resuelve_api.repository.EspecialidadRepository;
import com.resuelveya.resuelve_api.repository.TecnicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final EntityManager entityManager;


    public TecnicoService(
            TecnicoRepository tecnicoRepository,
            EspecialidadRepository especialidadRepository,
            EntityManager entityManager
    ) {
        this.tecnicoRepository = tecnicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.entityManager = entityManager;
    }


    // LISTAR TODOS
    public List<TecnicoResponseDto> obtenerTodos() {

        return tecnicoRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
    }


    // BUSCAR POR ID
    public TecnicoResponseDto obtenerPorId(Long id) {

        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Técnico no encontrado con ID: " + id
                        ));

        return convertirResponse(tecnico);
    }


    // CREAR
    public TecnicoResponseDto crear(TecnicoRequestDto request) {

        Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Especialidad no encontrada"
                        ));

        Tecnico tecnico = new Tecnico();

        tecnico.setNombre(request.getNombre());
        tecnico.setEmail(request.getEmail());
        tecnico.setTelefono(request.getTelefono());

        tecnico.setAniosExperiencia(request.getAniosExperiencia());
        tecnico.setCalificacionPromedio(request.getCalificacionPromedio());



        tecnico.setEspecialidad(especialidad);
        entityManager.persist(tecnico);

        entityManager.flush();


        return convertirResponse(tecnico);
    }


    // ACTUALIZAR
    public TecnicoResponseDto actualizar(
            Long id,
            TecnicoRequestDto request
    ) {

        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Técnico no encontrado con ID: " + id
                        ));

        Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Especialidad no encontrada"
                        ));

        tecnico.setNombre(request.getNombre());
        tecnico.setEmail(request.getEmail());
        tecnico.setTelefono(request.getTelefono());

        tecnico.setAniosExperiencia(request.getAniosExperiencia());
        tecnico.setCalificacionPromedio(request.getCalificacionPromedio());

        tecnico.setEspecialidad(especialidad);

        Tecnico tecnicoActualizado =
                tecnicoRepository.save(tecnico);


        return convertirResponse(tecnicoActualizado);
    }


    // ELIMINAR
    public void eliminar(Long id) {

        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Técnico no encontrado con ID: " + id
                        ));


        tecnicoRepository.delete(tecnico);
    }


    // CONVERSIÓN MANUAL ENTITY -> DTO (mapper)
    private TecnicoResponseDto convertirResponse(
            Tecnico tecnico
    ) {

        TecnicoResponseDto response =
                new TecnicoResponseDto();


        response.setId(tecnico.getId());
        response.setNombre(tecnico.getNombre());
        response.setEmail(tecnico.getEmail());
        response.setTelefono(tecnico.getTelefono());
        response.setAniosExperiencia(
                tecnico.getAniosExperiencia()
        );
        response.setCalificacionPromedio(
                tecnico.getCalificacionPromedio()
        );
        response.setEspecialidadId(tecnico.getEspecialidad().getId());
        response.setNombreEspecialidad(tecnico.getEspecialidad().getNombre());


        return response;
    }
}