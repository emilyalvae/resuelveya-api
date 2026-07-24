package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "especialidad")
@Getter
@Setter
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;


    @OneToMany(mappedBy = "especialidad", fetch = FetchType.LAZY)
    private List<Tecnico> tecnicos = new ArrayList<>();
}