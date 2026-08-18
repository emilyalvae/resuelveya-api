package com.resuelveya.resuelve_api.business.data.repository;

import com.resuelveya.resuelve_api.business.data.entity.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReseniaRepository extends JpaRepository<Resenia, Long> {

    List<Resenia> findByTecnicoIdOrderByCreatedAtDesc(Long tecnicoId);

    long countByTecnicoId(Long tecnicoId);

    @Query("SELECT AVG(r.calificacion) FROM Resenia r WHERE r.tecnico.id = :tecnicoId")
    Double calcularCalificacionPromedio(@Param("tecnicoId") Long tecnicoId);

    boolean existsByClienteIdAndTecnicoId(Long clienteId, Long tecnicoId);
}
