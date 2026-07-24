package com.resuelveya.resuelve_api.repository;

import com.resuelveya.resuelve_api.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    List<Tecnico> findByEspecialidadId(Long especialidadId);
}