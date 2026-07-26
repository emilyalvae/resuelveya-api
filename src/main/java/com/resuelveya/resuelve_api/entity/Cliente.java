package com.resuelveya.resuelve_api.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientes")

@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    @Column(name = "direccion_hogar", nullable = false)
    private String direccionHogar;

    // ya que en el futuro vamos asociar las solicitudes, se usara FetchType.LAZY para optimizar
    /*
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Solicitud> solicitudes;
    */

    public Cliente() {
        super();
    }

    public Cliente(String nombre, String email, String telefono, String direccionHogar) {
        super(nombre, email, telefono, Rol.CLIENTE);
        this.direccionHogar = direccionHogar;
    }

    public String getDireccionHogar() {
        return direccionHogar;
    }

    public void setDireccionHogar(String direccionHogar) {
        this.direccionHogar = direccionHogar;
    }
}