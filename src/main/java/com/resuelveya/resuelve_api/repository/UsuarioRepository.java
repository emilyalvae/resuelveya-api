package com.resuelveya.resuelve_api.repository;

import com.resuelveya.resuelve_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
