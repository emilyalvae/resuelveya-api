package com.resuelveya.resuelve_api.business.data.repository;

import com.resuelveya.resuelve_api.business.data.entity.Usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface  UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);

    //cONSULTA JPQL con parametros, paginacion y ordenamiento
    @Query(
            """
SELECT u 
FROM Usuario u 
WHERE LOWER(u.nombre) 
LIKE LOWER(CONCAT('%', :nombre, '%'))
"""
    )Page<Usuario> buscarUsuarios(
            @Param("nombre") String nombre,
            Pageable pageable
            );

}
