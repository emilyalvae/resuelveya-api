package com.resuelveya.resuelve_api.business.api.dto.tecnico;

import com.resuelveya.resuelve_api.business.api.dto.resenia.ReseniaResponseDto;
import com.resuelveya.resuelve_api.business.api.dto.servicio.ServicioResponseDto;

import java.util.List;

public record TecnicoCompletoResponseDto(
        Long id,
        String nombre,
        String email,
        String telefono,
        String fotoUrl,
        String direccion,
        String distrito,
        String ciudad,
        String presentacion,
        Integer aniosExperiencia,
        Double calificacionPromedio,
        String yapeNumero,
        String plinNumero,
        String titularPago,
        Long especialidadId,
        String especialidadNombre,
        List<ServicioResponseDto> servicios,
        List<ReseniaResponseDto> resenias
) {
}
