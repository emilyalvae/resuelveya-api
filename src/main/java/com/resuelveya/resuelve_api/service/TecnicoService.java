package com.resuelveya.resuelve_api.service;

import com.resuelveya.resuelve_api.entity.Tecnico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TecnicoService {

    @PersistenceContext
    private EntityManager entityManager;

    // Cumple: Configuración de Flush y Clear para batch/lotes
    @Transactional
    public void registrarTecnicosEnLote(List<Tecnico> tecnicos) {
        int batchSize = 20;
        for (int i = 0; i < tecnicos.size(); i++) {
            entityManager.persist(tecnicos.get(i));
            if (i % batchSize == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }
}