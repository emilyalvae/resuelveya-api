package com.resuelveya.resuelve_api.repository;

import com.resuelveya.resuelve_api.entity.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    // Buscar técnico por correo (heredado de Usuario)
    Optional<Tecnico> findByEmail(String email);

    // Validaciones de duplicados al CREAR
    boolean existsByEmail(String email);

    // Validaciones de duplicados al ACTUALIZAR (excluye el id actual)
    boolean existsByEmailAndIdNot(String email, Long id);

    // Consulta simple existente
    List<Tecnico> findByEspecialidadId(Long especialidadId);

    // Búsqueda avanzada paginada con filtros opcionales (JPQL)
    @Query("""
            SELECT t
            FROM Tecnico t
            WHERE (
                :especialidadId IS NULL
                OR t.especialidad.id = :especialidadId
            )
            AND (
                :nombre IS NULL
                OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))
            )
            """)
    Page<Tecnico> buscarTecnicos(
            @Param("especialidadId") Long especialidadId,
            @Param("nombre") String nombre,
            Pageable pageable
    );
}