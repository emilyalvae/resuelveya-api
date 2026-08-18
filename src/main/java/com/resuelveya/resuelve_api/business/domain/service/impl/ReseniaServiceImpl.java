package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.data.entity.Cliente;
import com.resuelveya.resuelve_api.business.data.entity.Resenia;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.repository.ClienteRepository;
import com.resuelveya.resuelve_api.business.data.repository.ReseniaRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.business.domain.mapper.ReseniaMapper;
import com.resuelveya.resuelve_api.business.domain.service.ReseniaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class ReseniaServiceImpl implements ReseniaService {

    private final ReseniaRepository reseniaRepository;
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ReseniaMapper reseniaMapper;

    public ReseniaServiceImpl(
            ReseniaRepository reseniaRepository,
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            ReseniaMapper reseniaMapper
    ) {
        this.reseniaRepository = reseniaRepository;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.reseniaMapper = reseniaMapper;
    }

    @Override
    public ReseniaResponseDto crearResenia(String emailCliente, ReseniaRequestDto requestDto) {
        Cliente cliente = clienteRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con email: " + emailCliente));

        Tecnico tecnico = tecnicoRepository.findById(requestDto.tecnicoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Técnico no encontrado con ID: " + requestDto.tecnicoId()));

        Resenia resenia = reseniaMapper.toEntity(requestDto);
        resenia.setCliente(cliente);
        resenia.setTecnico(tecnico);

        Resenia guardada = reseniaRepository.save(resenia);

        // Recalcular promedio de calificación del técnico
        Double nuevoPromedio = reseniaRepository.calcularCalificacionPromedio(tecnico.getId());
        if (nuevoPromedio != null) {
            BigDecimal bd = BigDecimal.valueOf(nuevoPromedio).setScale(1, RoundingMode.HALF_UP);
            tecnico.setCalificacionPromedio(bd.doubleValue());
            tecnicoRepository.save(tecnico);
        }

        return reseniaMapper.toResponseDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReseniaResponseDto> listarReseniasPorTecnico(Long tecnicoId) {
        return reseniaRepository.findByTecnicoIdOrderByCreatedAtDesc(tecnicoId)
                .stream()
                .map(reseniaMapper::toResponseDto)
                .toList();
    }
}
