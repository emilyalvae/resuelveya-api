package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioPublicoDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioResponseDto;
import com.resuelveya.resuelve_api.business.data.entity.Servicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicioMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    @Mapping(source = "tecnico.id", target = "tecnicoId")
    @Mapping(source = "tecnico.nombre", target = "tecnicoNombre")
    ServicioResponseDto toResponseDto(Servicio servicio);

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    @Mapping(source = "tecnico.id", target = "tecnicoId")
    @Mapping(source = "tecnico.nombre", target = "tecnicoNombre")
    @Mapping(source = "tecnico.fotoUrl", target = "tecnicoFotoUrl")
    @Mapping(source = "tecnico.distrito", target = "tecnicoDistrito")
    @Mapping(source = "tecnico.ciudad", target = "tecnicoCiudad")
    @Mapping(source = "tecnico.calificacionPromedio", target = "tecnicoCalificacionPromedio")
    ServicioPublicoDto toPublicoDto(Servicio servicio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Servicio toEntity(ServicioRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void actualizarEntidad(ServicioRequestDto dto, @MappingTarget Servicio servicio);
}
