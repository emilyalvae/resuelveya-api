package com.resuelveya.resuelve_api.business.data.repository;

import com.resuelveya.resuelve_api.business.data.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    @Modifying
    @Query(value = "INSERT IGNORE INTO cliente (usuario_id) VALUES (:usuarioId)", nativeQuery = true)
    void registrarFilaClienteSiNoExiste(@Param("usuarioId") Long usuarioId);
}