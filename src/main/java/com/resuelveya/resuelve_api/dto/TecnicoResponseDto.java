package com.resuelveya.resuelve_api.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TecnicoResponseDto {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Integer aniosExperiencia;
    private Double calificacionPromedio;
    private Long especialidadId;
    private String nombreEspecialidad;
}