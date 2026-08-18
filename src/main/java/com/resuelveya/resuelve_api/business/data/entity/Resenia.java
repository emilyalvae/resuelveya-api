package com.resuelveya.resuelve_api.business.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(length = 1000)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Resenia(Integer calificacion, String comentario, Cliente cliente, Tecnico tecnico) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.createdAt = LocalDateTime.now();
    }
}
