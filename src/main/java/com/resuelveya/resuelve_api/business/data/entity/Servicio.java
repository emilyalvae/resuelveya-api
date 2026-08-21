package com.resuelveya.resuelve_api.business.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "precio_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioEstimado;

    @Column(name = "tiempo_estimado", length = 50)
    private String tiempoEstimado;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Servicio(String titulo, String descripcion, BigDecimal precioEstimado, String tiempoEstimado,
            Categoria categoria, Tecnico tecnico) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precioEstimado = precioEstimado;
        this.tiempoEstimado = tiempoEstimado;
        this.categoria = categoria;
        this.tecnico = tecnico;
        this.activo = true;
        this.createdAt = LocalDateTime.now();
    }
}
