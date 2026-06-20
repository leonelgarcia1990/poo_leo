package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.entity.PistaEntity;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class PistaDAO {

    public List<PistaEntity> listarPistas() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("select p from PistaEntity p order by p.id", PistaEntity.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("No se pudo leer el catálogo de pistas: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
