package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.cliente.ClienteRequestDto;
import com.resuelveya.resuelve_api.business.api.dto.cliente.ClienteResponseDto;
import com.resuelveya.resuelve_api.business.data.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", implementationName = "ClienteBusinessMapperImpl")
public interface ClienteMapper {

    ClienteResponseDto toResponseDto(Cliente cliente);

    List<ClienteResponseDto> toResponseDtoList(List<Cliente> clientes);

    Cliente toEntity(ClienteRequestDto requestDto);

    void updateEntityFromDto(ClienteRequestDto requestDto, @MappingTarget Cliente cliente);
}