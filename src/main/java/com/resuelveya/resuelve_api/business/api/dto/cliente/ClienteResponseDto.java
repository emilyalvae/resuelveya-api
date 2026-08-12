package com.resuelveya.resuelve_api.business.api.dto.cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDto {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String rol;
    private String direccionHogar;
}