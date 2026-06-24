package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Pista;

import jakarta.persistence.EntityManager;

public class InicializadorBaseDatosDAO {

    public void inicializar() {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();
            sembrarCatalogo(em);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No se pudo inicializar la base de datos: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void sembrarCatalogo(EntityManager em) {
        if (tablaVacia(em, "Caballo")) {
            em.persist(new Caballo("Relámpago", "Velocista", 92, 55));
            em.persist(new Caballo("Tormenta", "Resistente", 68, 92));
            em.persist(new Caballo("Furia", "Velocista", 90, 50));
            em.persist(new Caballo("Centella", "Equilibrado", 80, 80));
        }
        if (tablaVacia(em, "Pista")) {
            em.persist(new Pista("Gran Premio Nacional", 2500));
            em.persist(new Pista("Gran Premio Carlos Pellegrini", 2400));
            em.persist(new Pista("Gran Premio Jockey Club", 2000));
        }
    }

    private boolean tablaVacia(EntityManager em, String entidad) {
        Long cantidad = em.createQuery("select count(e) from " + entidad + " e", Long.class)
                .getSingleResult();
        return cantidad == 0;
    }
}
