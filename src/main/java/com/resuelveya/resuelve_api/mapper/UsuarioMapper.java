package com.resuelveya.resuelve_api.mapper;

import com.resuelveya.resuelve_api.dto.request.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.dto.response.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.entity.Cliente;
import com.resuelveya.resuelve_api.entity.Tecnico;
import com.resuelveya.resuelve_api.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO toResponseDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    void actualizarEntidad(UsuarioRequestDTO usuarioRequestDTO, @MappingTarget Usuario usuario);

    // --- Conversiones específicas ---
    @Mapping(target = "id", ignore = true)
    Cliente toCliente(UsuarioRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    Tecnico toTecnico(UsuarioRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    Usuario toAdmin(UsuarioRequestDTO dto);
}
