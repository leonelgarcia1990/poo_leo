package com.uade.carreras.dao;

import com.uade.carreras.db.JPAUtil;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.entidad.CaballoEntity;
import com.uade.carreras.entidad.CarreraEntity;
import com.uade.carreras.entidad.JugadorEntity;
import com.uade.carreras.entidad.PistaEntity;
import com.uade.carreras.entidad.PosicionEntity;

import jakarta.persistence.EntityManager;

import java.util.List;

public class CarreraDAO {

    public void guardarResultado(String nombreJugador, String emailJugador, int puntajeTotal,
                                 int pistaId, int caballoElegidoId,
                                 int caballoGanadorId, boolean ganoElJugador,
                                 PosicionDTO[] tablaPosiciones) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();

            JugadorEntity jugador = obtenerOCrearJugador(em, nombreJugador, emailJugador, puntajeTotal);

            PistaEntity pista = em.getReference(PistaEntity.class, pistaId);
            CaballoEntity caballoElegido = em.getReference(CaballoEntity.class, caballoElegidoId);
            CaballoEntity caballoGanador = em.getReference(CaballoEntity.class, caballoGanadorId);

            CarreraEntity carrera = new CarreraEntity(jugador, pista, caballoElegido,
                    caballoGanador, ganoElJugador);
            em.persist(carrera);

            for (PosicionDTO p : tablaPosiciones) {
                CaballoEntity caballo = em.getReference(CaballoEntity.class, p.getCaballoId());
                PosicionEntity posicion = new PosicionEntity(carrera, caballo,
                        p.getPosicion(), p.getPuntos(), p.esDelJugador());
                em.persist(posicion);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    private JugadorEntity obtenerOCrearJugador(EntityManager em, String nombre, String email,
                                               int puntajeTotal) {
        List<JugadorEntity> jugadores = em.createQuery(
                "select j from JugadorEntity j where j.email = :email", JugadorEntity.class)
                .setParameter("email", email)
                .getResultList();
        JugadorEntity jugador;
        if (jugadores.isEmpty()) {
            jugador = new JugadorEntity(nombre, email, puntajeTotal);
            em.persist(jugador);
        } else {
            jugador = jugadores.get(0);
            jugador.setPuntajeAcumulado(puntajeTotal);
        }
        return jugador;
    }
}
