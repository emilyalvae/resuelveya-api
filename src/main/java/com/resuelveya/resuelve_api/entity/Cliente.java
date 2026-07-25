package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    private String direccionHogar;

    public Cliente() {
    }

    public Cliente(String nombre, String email, String telefono, String direccionHogar) {
        super(nombre, email, telefono);
        this.direccionHogar = direccionHogar;
    }

    public String getDireccionHogar() { return direccionHogar; }
    public void setDireccionHogar(String direccionHogar) { this.direccionHogar = direccionHogar; }
}