package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tecnico")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
public class Tecnico extends Usuario {

    private Integer aniosExperiencia;
    private Double calificacionPromedio;

//Solo cargamos cuando la necesitamos
    // Cumple: Rúbrica @ManyToOne + Fetching LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;

    public Tecnico() {
    }

    public Tecnico(String nombre, String email, String telefono, Integer aniosExperiencia, Double calificacionPromedio, Especialidad especialidad) {
        super(nombre, email, telefono);
        this.aniosExperiencia = aniosExperiencia;
        this.calificacionPromedio = calificacionPromedio;
        this.especialidad = especialidad;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }

    public void setCalificacionPromedio(Double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }
}