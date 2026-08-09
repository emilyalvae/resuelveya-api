package com.resuelveya.resuelve_api.business.data.entity;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tecnico")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Getter
@Setter
@AllArgsConstructor
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
    public Tecnico(String nombre, String email,String password, String telefono, Integer aniosExperiencia, Double calificacionPromedio, Especialidad especialidad) {
        super(nombre, email, telefono,password , com.resuelveya.resuelve_api.business.data.entity.enums.Rol.TECNICO);
        this.aniosExperiencia = aniosExperiencia;
        this.calificacionPromedio = calificacionPromedio;
        this.especialidad = especialidad;
    }
}