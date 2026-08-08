package com.resuelveya.resuelve_api.service.impl;

import com.resuelveya.resuelve_api.dto.request.TecnicoRequestDto;
import com.resuelveya.resuelve_api.dto.response.TecnicoResponseDto;
import com.resuelveya.resuelve_api.entity.Especialidad;
import com.resuelveya.resuelve_api.entity.Tecnico;
import com.resuelveya.resuelve_api.exception.RecursoDuplicadoException;
import com.resuelveya.resuelve_api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.mapper.TecnicoMapper;
import com.resuelveya.resuelve_api.repository.EspecialidadRepository;
import com.resuelveya.resuelve_api.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.service.TecnicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final EspecialidadRepository especialidadRepository;
    private final TecnicoMapper tecnicoMapper;

    public TecnicoServiceImpl(
            TecnicoRepository tecnicoRepository,
            EspecialidadRepository especialidadRepository,
            TecnicoMapper tecnicoMapper
    ) {
        this.tecnicoRepository = tecnicoRepository;
        this.especialidadRepository = especialidadRepository;
        this.tecnicoMapper = tecnicoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TecnicoResponseDto> obtenerTodos() {
        return tecnicoRepository.findAll()
                .stream()
                .map(tecnicoMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TecnicoResponseDto obtenerPorId(Long id) {
        Tecnico tecnico = buscarTecnicoPorId(id);

        return tecnicoMapper.toResponseDto(tecnico);
    }

    @Override
    public TecnicoResponseDto crear(
            TecnicoRequestDto requestDto
    ) {
        validarDuplicadosAlCrear(requestDto);

        Especialidad especialidad = buscarEspecialidadPorId(requestDto.especialidadId());

        Tecnico tecnico = tecnicoMapper.toEntity(requestDto);
        tecnico.setEspecialidad(especialidad);

        Tecnico tecnicoGuardado = tecnicoRepository.save(tecnico);

        return tecnicoMapper.toResponseDto(tecnicoGuardado);
    }

    @Override
    public TecnicoResponseDto actualizar(
            Long id,
            TecnicoRequestDto requestDto
    ) {
        Tecnico tecnico = buscarTecnicoPorId(id);

        validarDuplicadosAlActualizar(id, requestDto);

        Especialidad especialidad = buscarEspecialidadPorId(requestDto.especialidadId());

        tecnicoMapper.actualizarEntidad(requestDto, tecnico);
        tecnico.setEspecialidad(especialidad);

        Tecnico tecnicoActualizado = tecnicoRepository.save(tecnico);

        return tecnicoMapper.toResponseDto(tecnicoActualizado);
    }

    @Override
    public void eliminar(Long id) {
        Tecnico tecnico = buscarTecnicoPorId(id);

        tecnicoRepository.delete(tecnico);
    }

    @Override
    @Transactional(readOnly = true)
    public TecnicoResponseDto buscarPorEmail(
            String email
    ) {
        Tecnico tecnico = tecnicoRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró un técnico con el correo: " + email
                        )
                );

        return tecnicoMapper.toResponseDto(tecnico);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TecnicoResponseDto> consultar(
            Long especialidadId,
            String nombre,
            Pageable pageable
    ) {
        String nombreNormalizado =
                nombre == null || nombre.isBlank()
                        ? null
                        : nombre.trim();

        return tecnicoRepository
                .buscarTecnicos(
                        especialidadId,
                        nombreNormalizado,
                        pageable
                )
                .map(tecnicoMapper::toResponseDto);
    }

    private Tecnico buscarTecnicoPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró el técnico con id: " + id
                        )
                );
    }

    private Especialidad buscarEspecialidadPorId(Long especialidadId) {
        return especialidadRepository.findById(especialidadId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No se encontró la especialidad con id: " + especialidadId
                        )
                );
    }

    private void validarDuplicadosAlCrear(
            TecnicoRequestDto requestDto
    ) {
        if (requestDto.email() != null
                && tecnicoRepository.existsByEmail(
                requestDto.email()
        )) {
            throw new RecursoDuplicadoException(
                    "El correo electrónico ya está registrado"
            );
        }
    }

    private void validarDuplicadosAlActualizar(
            Long id,
            TecnicoRequestDto requestDto
    ) {
        if (requestDto.email() != null
                && tecnicoRepository.existsByEmailAndIdNot(
                requestDto.email(),
                id
        )) {
            throw new RecursoDuplicadoException(
                    "El correo electrónico ya está registrado"
            );
        }
    }
}