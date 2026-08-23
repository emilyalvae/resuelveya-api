package com.resuelveya.resuelve_api.business.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaResponseDto;
import com.resuelveya.resuelve_api.business.api.exception.RecursoNoEncontradoException;
import com.resuelveya.resuelve_api.business.data.entity.Categoria;
import com.resuelveya.resuelve_api.business.data.repository.CategoriaRepository;
import com.resuelveya.resuelve_api.business.domain.mapper.CategoriaMapper;
import com.resuelveya.resuelve_api.business.domain.service.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository especialidadRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServiceImpl(CategoriaRepository especialidadRepository, CategoriaMapper categoriaMapper) {
        this.especialidadRepository = especialidadRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDto> listarTodas() {
        return especialidadRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponseDto> listarPaginado(String nombre, Pageable pageable) {
        String nombreNormalizado = (nombre == null || nombre.isBlank()) ? null : nombre.trim();
        return especialidadRepository.buscarCategorias(nombreNormalizado, pageable)
                .map(categoriaMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDto obtenerPorId(Long id) {
        Categoria especialidad = buscarPorId(id);
        return categoriaMapper.toResponseDto(especialidad);
    }

    @Override
    public CategoriaResponseDto crear(CategoriaRequestDto requestDto) {
        Categoria especialidad = categoriaMapper.toEntity(requestDto);
        Categoria guardada = especialidadRepository.save(especialidad);
        return categoriaMapper.toResponseDto(guardada);
    }

    @Override
    public CategoriaResponseDto actualizar(Long id, CategoriaRequestDto requestDto) {
        Categoria especialidad = buscarPorId(id);
        categoriaMapper.actualizarEntidad(requestDto, especialidad);
        Categoria actualizada = especialidadRepository.save(especialidad);
        return categoriaMapper.toResponseDto(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        Categoria especialidad = buscarPorId(id);
        especialidadRepository.delete(especialidad);
    }

    private Categoria buscarPorId(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id));
    }
}
