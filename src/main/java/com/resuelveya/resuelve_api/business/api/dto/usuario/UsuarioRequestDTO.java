<<<<<<<< HEAD:src/main/java/com/resuelveya/resuelve_api/business/api/dto/request/UsuarioRequestDTO.java
package com.resuelveya.resuelve_api.business.api.dto.request;

import com.resuelveya.resuelve_api.business.data.entity.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
========
package com.resuelveya.resuelve_api.business.api.dto.usuario;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
import jakarta.validation.constraints.*;
>>>>>>>> origin/develop:src/main/java/com/resuelveya/resuelve_api/business/api/dto/usuario/UsuarioRequestDTO.java

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public record UsuarioRequestDTO (

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80,message = "El nombre no debe superar los 80 caracteres")
    String nombre,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Ingresa un email valido")
    @Size(max = 100,message = "El nombre no debe superar los 100 caracteres")
    String email,

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "La contraseña debe tener al menos una mayúscula, una minúscula y un número"
    )
    String password,


    @Size(max = 9,message = "El telefono no debe superar los 9 caracteres")
    String telefono,

    @NotNull(message = "El rol es obligatorio")
    Rol rol
){}
