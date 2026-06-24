package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.model.Pista;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class PistaDAO {

    public List<Pista> listarPistas() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            return em.createQuery("select p from Pista p order by p.id", Pista.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("No se pudo leer el catálogo de pistas: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
