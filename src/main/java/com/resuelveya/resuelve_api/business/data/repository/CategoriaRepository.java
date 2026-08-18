package com.resuelveya.resuelve_api.business.data.repository;

import com.resuelveya.resuelve_api.business.data.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}