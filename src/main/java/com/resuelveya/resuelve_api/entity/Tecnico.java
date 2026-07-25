package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tecnico")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tecnico extends Usuario {

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(name = "calificacion_promedio")
    private Double calificacionPromedio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
}