package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.categoria.CategoriaResponseDto;
import com.resuelveya.resuelve_api.business.data.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "totalTecnicos", expression = "java(especialidad.getTecnicos() != null ? especialidad.getTecnicos().size() : 0)")
    CategoriaResponseDto toResponseDto(Categoria especialidad);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tecnicos", ignore = true)
    Categoria toEntity(CategoriaRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tecnicos", ignore = true)
    void actualizarEntidad(CategoriaRequestDto dto, @MappingTarget Categoria especialidad);
}
