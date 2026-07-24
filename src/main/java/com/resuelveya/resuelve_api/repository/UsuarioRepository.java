package com.resuelveya.resuelve_api.repository;

import com.resuelveya.resuelve_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByEmailContainingIgnoreCase(String email);

}
