package com.uade.carreras.dao;

import com.uade.carreras.db.JPAUtil;
import com.uade.carreras.entidad.CaballoEntity;
import com.uade.carreras.entidad.PistaEntity;

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
        if (tablaVacia(em, "CaballoEntity")) {
            em.persist(new CaballoEntity("Relámpago", "VELOCISTA", 92, 55));
            em.persist(new CaballoEntity("Tormenta", "RESISTENTE", 68, 92));
            em.persist(new CaballoEntity("Furia", "VELOCISTA", 90, 50));
            em.persist(new CaballoEntity("Centella", "EQUILIBRADO", 80, 80));
        }
        if (tablaVacia(em, "PistaEntity")) {
            em.persist(new PistaEntity("Gran Premio Nacional", 2500));
            em.persist(new PistaEntity("Gran Premio Carlos Pellegrini", 2400));
            em.persist(new PistaEntity("Gran Premio Jockey Club", 2000));
        }
    }

    private boolean tablaVacia(EntityManager em, String entidad) {
        Long cantidad = em.createQuery("select count(e) from " + entidad + " e", Long.class)
                .getSingleResult();
        return cantidad == 0;
    }
}
