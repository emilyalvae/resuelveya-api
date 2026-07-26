package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tecnico")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter

@AllArgsConstructor
public class Tecnico extends Usuario {

    private Integer aniosExperiencia;
    private Double calificacionPromedio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;


    public Tecnico() {
        super();
        this.setRol(Rol.TECNICO);
    }

    public Tecnico(String nombre, String email, String telefono, Integer aniosExperiencia, Double calificacionPromedio, Especialidad especialidad) {
        super(nombre, email, telefono,Rol.TECNICO);
        this.aniosExperiencia = aniosExperiencia;
        this.calificacionPromedio = calificacionPromedio;
        this.especialidad = especialidad;
    }


}