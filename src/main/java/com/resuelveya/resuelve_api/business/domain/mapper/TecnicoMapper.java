package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.request.TecnicoRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.response.TecnicoResponseDto;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TecnicoMapper {

    // Entidad -> ResponseDto
    @Mapping(source = "especialidad.id", target = "especialidadId")
    @Mapping(source = "especialidad.nombre", target = "nombreEspecialidad")
    TecnicoResponseDto toResponseDto(Tecnico tecnico);

    // RequestDto -> Entidad (al crear)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Tecnico toEntity(TecnicoRequestDto requestDto);

    // RequestDto -> Entidad (al actualizar sobre un objeto existente)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "rol", ignore = true)
    void actualizarEntidad(
            TecnicoRequestDto requestDto,
            @MappingTarget Tecnico tecnico
    );
}