package com.resuelveya.resuelve_api.business.domain.mapper;

import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.entity.Cliente;
import com.resuelveya.resuelve_api.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
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
