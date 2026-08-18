package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.data.entity.Resenia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReseniaMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "cliente.fotoUrl", target = "clienteFotoUrl")
    @Mapping(source = "tecnico.id", target = "tecnicoId")
    @Mapping(source = "servicio.id", target = "servicioId")
    @Mapping(source = "servicio.titulo", target = "servicioTitulo")
    ReseniaResponseDto toResponseDto(Resenia resenia);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "servicio", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Resenia toEntity(ReseniaRequestDto dto);
}

