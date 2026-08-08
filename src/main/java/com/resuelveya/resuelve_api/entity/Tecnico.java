package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tecnico")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Tecnico extends Usuario {

    @Column(name = "anios_experiencia", nullable = false)
    private Integer aniosExperiencia;

    @Column(name = "calificacion_promedio", nullable = false)
    private Double calificacionPromedio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    public Tecnico() {
        super();
        this.setRol(Rol.TECNICO);
    }
}