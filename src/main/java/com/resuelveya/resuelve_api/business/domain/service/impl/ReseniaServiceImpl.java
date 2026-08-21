package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.api.exception.OperacionNoPermitidaException;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.data.entity.Cliente;
import com.resuelveya.resuelve_api.business.data.entity.Resenia;
import com.resuelveya.resuelve_api.business.data.entity.Servicio;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.repository.ClienteRepository;
import com.resuelveya.resuelve_api.business.data.repository.ReseniaRepository;
import com.resuelveya.resuelve_api.business.data.repository.ServicioRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
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
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReseniaMapper reseniaMapper;

    public ReseniaServiceImpl(
            ReseniaRepository reseniaRepository,
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            ServicioRepository servicioRepository,
            UsuarioRepository usuarioRepository,
            ReseniaMapper reseniaMapper
    ) {
        this.reseniaRepository = reseniaRepository;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.reseniaMapper = reseniaMapper;
    }

    private Cliente obtenerOCrearCliente(String emailCliente) {
        return clienteRepository.findByEmail(emailCliente)
                .orElseGet(() -> {
                    var usuario = usuarioRepository.findByEmailIgnoreCase(emailCliente)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con email: " + emailCliente));
                    clienteRepository.registrarFilaClienteSiNoExiste(usuario.getId());
                    return clienteRepository.findById(usuario.getId())
                            .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con email: " + emailCliente));
                });
    }

    @Override
    public ReseniaResponseDto crearResenia(String emailCliente, ReseniaRequestDto requestDto) {
        Cliente cliente = obtenerOCrearCliente(emailCliente);

        Tecnico tecnico = null;
        Servicio servicio = null;

        if (requestDto.servicioId() != null) {
            servicio = servicioRepository.findById(requestDto.servicioId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con ID: " + requestDto.servicioId()));
            tecnico = servicio.getTecnico();

            // Evita registros duplicados garantizando que un cliente solo pueda opinar una vez por servicio
            if (reseniaRepository.existsByClienteIdAndServicioId(cliente.getId(), servicio.getId())) {
                throw new OperacionNoPermitidaException("Ya has registrado una reseña para este servicio. Solo se permite una reseña por cliente.");
            }
        } else if (requestDto.tecnicoId() != null) {
            tecnico = tecnicoRepository.findById(requestDto.tecnicoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Técnico no encontrado con ID: " + requestDto.tecnicoId()));

            // Evita registros duplicados garantizando que un cliente solo pueda opinar una vez por técnico
            if (reseniaRepository.existsByClienteIdAndTecnicoId(cliente.getId(), tecnico.getId())) {
                throw new OperacionNoPermitidaException("Ya has registrado una reseña para este técnico. Solo se permite una reseña por cliente.");
            }
        } else {
            throw new OperacionNoPermitidaException("Debe especificar al menos un servicio o un técnico para calificar.");
        }

        Resenia resenia = reseniaMapper.toEntity(requestDto);
        resenia.setCliente(cliente);
        resenia.setTecnico(tecnico);
        resenia.setServicio(servicio);

        Resenia guardada = reseniaRepository.save(resenia);

        // Recalcular promedio de calificación del técnico
        recalcularPromedioTecnico(tecnico.getId());

        return reseniaMapper.toResponseDto(guardada);
    }

    @Override
    public ReseniaResponseDto actualizarResenia(String emailCliente, Long reseniaId, ReseniaRequestDto requestDto) {
        Cliente cliente = obtenerOCrearCliente(emailCliente);

        Resenia resenia = reseniaRepository.findById(reseniaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reseña no encontrada con ID: " + reseniaId));

        if (!resenia.getCliente().getId().equals(cliente.getId())) {
            throw new OperacionNoPermitidaException("No tienes permisos para modificar esta reseña.");
        }

        resenia.setCalificacion(requestDto.calificacion());
        resenia.setComentario(requestDto.comentario());

        Resenia actualizada = reseniaRepository.save(resenia);

        if (resenia.getTecnico() != null) {
            recalcularPromedioTecnico(resenia.getTecnico().getId());
        }

        return reseniaMapper.toResponseDto(actualizada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReseniaResponseDto> listarReseniasPorServicio(Long servicioId) {
        return reseniaRepository.findByServicioIdOrderByCreatedAtDesc(servicioId)
                .stream()
                .map(reseniaMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReseniaResponseDto> listarReseniasPorTecnico(Long tecnicoId) {
        return reseniaRepository.findByTecnicoIdOrderByCreatedAtDesc(tecnicoId)
                .stream()
                .map(reseniaMapper::toResponseDto)
                .toList();
    }

    private void recalcularPromedioTecnico(Long tecnicoId) {
        Double nuevoPromedio = reseniaRepository.calcularCalificacionPromedio(tecnicoId);
        if (nuevoPromedio != null) {
            BigDecimal bd = BigDecimal.valueOf(nuevoPromedio).setScale(1, RoundingMode.HALF_UP);
            tecnicoRepository.findById(tecnicoId).ifPresent(t -> {
                t.setCalificacionPromedio(bd.doubleValue());
                tecnicoRepository.save(t);
            });
        }
    }
}

