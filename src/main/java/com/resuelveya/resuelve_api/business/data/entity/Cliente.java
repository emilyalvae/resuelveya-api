package com.resuelveya.resuelve_api.business.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    @Column(name = "direccion_hogar", length = 200)
    private String direccionHogar;

    // Requerimiento T1: Relación @OneToMany optimizada con FetchType.LAZY
   // @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //private List<SolicitudServicio> solicitudes = new ArrayList<>();
}