package com.resuelveya.resuelve_api.business.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "usuario_id")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
}