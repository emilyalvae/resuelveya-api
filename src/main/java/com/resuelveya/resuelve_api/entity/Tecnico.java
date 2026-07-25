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

    private Integer aniosExperiencia;
    private Double calificacionPromedio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;



    public Tecnico(String nombre, String email, String telefono, Integer aniosExperiencia, Double calificacionPromedio, Especialidad especialidad) {
        super(nombre, email, telefono);
        this.aniosExperiencia = aniosExperiencia;
        this.calificacionPromedio = calificacionPromedio;
        this.especialidad = especialidad;
    }

    public Integer getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(Integer aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }

    public Double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(Double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }
}