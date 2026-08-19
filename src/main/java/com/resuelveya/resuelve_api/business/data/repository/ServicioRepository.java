package com.resuelveya.resuelve_api.business.data.repository;

import com.resuelveya.resuelve_api.business.data.entity.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByTecnicoId(Long tecnicoId);

    List<Servicio> findByTecnicoIdAndActivoTrue(Long tecnicoId);

    List<Servicio> findByTecnicoEmail(String email);

    Optional<Servicio> findByIdAndTecnicoEmail(Long id, String email);

    @Query("""
            SELECT s
            FROM Servicio s
            JOIN s.tecnico t
            JOIN s.categoria c
            WHERE s.activo = true
              AND (
                  :query IS NULL
                  OR LOWER(s.titulo) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(s.descripcion) LIKE LOWER(CONCAT('%', :query, '%'))
                  OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :query, '%'))
              )
              AND (:categoriaId IS NULL OR c.id = :categoriaId)
              AND (:distrito IS NULL OR LOWER(t.ciudad) LIKE LOWER(CONCAT('%', :distrito, '%')) OR LOWER(t.codigoUbigeo) LIKE LOWER(CONCAT('%', :distrito, '%')))
              AND (:precioMin IS NULL OR s.precioEstimado >= :precioMin)
              AND (:precioMax IS NULL OR s.precioEstimado <= :precioMax)
            """)
    Page<Servicio> buscarServiciosPublicos(
            @Param("query") String query,
            @Param("categoriaId") Long categoriaId,
            @Param("distrito") String distrito,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable
    );
}
