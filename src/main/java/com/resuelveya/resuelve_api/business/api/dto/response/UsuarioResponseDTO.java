<<<<<<<< HEAD:src/main/java/com/resuelveya/resuelve_api/business/api/dto/response/UsuarioResponseDTO.java
package com.resuelveya.resuelve_api.business.api.dto.response;

import com.resuelveya.resuelve_api.business.data.entity.Rol;
========
package com.resuelveya.resuelve_api.business.api.dto.usuario;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
>>>>>>>> origin/develop:src/main/java/com/resuelveya/resuelve_api/business/api/dto/usuario/UsuarioResponseDTO.java

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String telefono,
        Rol rol
) {
}
