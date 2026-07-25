package com.resuelveya.resuelve_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80,message = "El nombre no debe superar los 80 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Ingresa un email valido")
    @Size(max = 100,message = "El nombre no debe superar los 100 caracteres")
    private String email;


    @Size(max = 9,message = "El telefono no debe superar los 9 caracteres")
    private String telefono;
}
