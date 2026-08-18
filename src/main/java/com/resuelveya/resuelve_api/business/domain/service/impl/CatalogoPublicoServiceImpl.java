package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioPublicoDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoCompletoResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.tecnico.TecnicoPublicoDto;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.repository.ReseniaRepository;
import com.resuelveya.resuelve_api.business.data.repository.ServicioRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.business.domain.mapper.ReseniaMapper;
import com.resuelveya.resuelve_api.business.domain.mapper.ServicioMapper;
import com.resuelveya.resuelve_api.business.domain.service.CatalogoPublicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CatalogoPublicoServiceImpl implements CatalogoPublicoService {

    private final ServicioRepository servicioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ReseniaRepository reseniaRepository;
    private final ServicioMapper servicioMapper;
    private final ReseniaMapper reseniaMapper;

    public CatalogoPublicoServiceImpl(
            ServicioRepository servicioRepository,
            TecnicoRepository tecnicoRepository,
            ReseniaRepository reseniaRepository,
            ServicioMapper servicioMapper,
            ReseniaMapper reseniaMapper
    ) {
        this.servicioRepository = servicioRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.reseniaRepository = reseniaRepository;
        this.servicioMapper = servicioMapper;
        this.reseniaMapper = reseniaMapper;
    }

    @Override
    public Page<ServicioPublicoDto> buscarServicios(
            String query,
            Long categoriaId,
            String distrito,
            BigDecimal precioMin,
            BigDecimal precioMax,
            Pageable pageable
    ) {
        String queryNormalizada = (query == null || query.isBlank()) ? null : query.trim();
        String distritoNormalizado = (distrito == null || distrito.isBlank()) ? null : distrito.trim();

        return servicioRepository.buscarServiciosPublicos(
                queryNormalizada,
                categoriaId,
                distritoNormalizado,
                precioMin,
                precioMax,
                pageable
        ).map(servicioMapper::toPublicoDto);
    }

    @Override
    public Page<TecnicoPublicoDto> listarTecnicosPublicos(
            Long categoriaId,
            String nombre,
            Pageable pageable
    ) {
        String nombreNormalizado = (nombre == null || nombre.isBlank()) ? null : nombre.trim();

        return tecnicoRepository.buscarTecnicos(categoriaId, nombreNormalizado, pageable)
                .map(tecnico -> {
                    long totalResenias = reseniaRepository.countByTecnicoId(tecnico.getId());
                    return new TecnicoPublicoDto(
                            tecnico.getId(),
                            tecnico.getNombre(),
                            tecnico.getFotoUrl(),
                            tecnico.getDistrito(),
                            tecnico.getCiudad(),
                            tecnico.getPresentacion(),
                            tecnico.getAniosExperiencia(),
                            tecnico.getCalificacionPromedio(),
                            totalResenias,
                            tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getId() : null,
                            tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getNombre() : null
                    );
                });
    }

    @Override
    public TecnicoCompletoResponseDto obtenerTecnicoCompleto(Long tecnicoId) {
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Técnico no encontrado con ID: " + tecnicoId));

        List<ServicioResponseDto> servicios = servicioRepository.findByTecnicoIdAndActivoTrue(tecnicoId)
                .stream()
                .map(servicioMapper::toResponseDto)
                .toList();

        List<ReseniaResponseDto> resenias = reseniaRepository.findByTecnicoIdOrderByCreatedAtDesc(tecnicoId)
                .stream()
                .map(reseniaMapper::toResponseDto)
                .toList();

        return new TecnicoCompletoResponseDto(
                tecnico.getId(),
                tecnico.getNombre(),
                tecnico.getEmail(),
                tecnico.getTelefono(),
                tecnico.getFotoUrl(),
                tecnico.getDireccion(),
                tecnico.getDistrito(),
                tecnico.getCiudad(),
                tecnico.getPresentacion(),
                tecnico.getAniosExperiencia(),
                tecnico.getCalificacionPromedio(),
                tecnico.getYapeNumero(),
                tecnico.getPlinNumero(),
                tecnico.getTitularPago(),
                tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getId() : null,
                tecnico.getEspecialidad() != null ? tecnico.getEspecialidad().getNombre() : null,
                servicios,
                resenias
        );
    }
}
