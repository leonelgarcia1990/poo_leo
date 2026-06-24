package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Carrera;
import com.uade.carreras.model.Jugador;
import com.uade.carreras.model.Pista;
import com.uade.carreras.model.Posicion;

import jakarta.persistence.EntityManager;

import java.util.List;

public class CarreraDAO {

    public void guardarResultado(Carrera carrera, String nombreJugador, String emailJugador,
                                 int puntajeTotal, int pistaId, int caballoElegidoId,
                                 int caballoGanadorId, boolean ganoElJugador,
                                 PosicionDTO[] tablaPosiciones) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            em.getTransaction().begin();

            Jugador jugador = obtenerOCrearJugador(em, nombreJugador, emailJugador, puntajeTotal);

            Pista pista = em.getReference(Pista.class, pistaId);
            Caballo caballoElegido = em.getReference(Caballo.class, caballoElegidoId);
            Caballo caballoGanador = em.getReference(Caballo.class, caballoGanadorId);

            carrera.confirmar(jugador, pista, caballoElegido, caballoGanador, ganoElJugador);

            for (PosicionDTO p : tablaPosiciones) {
                Caballo caballo = em.getReference(Caballo.class, p.getCaballoId());
                Posicion posicion = new Posicion(carrera, caballo,
                        p.getPosicion(), p.getPuntos(), p.esDelJugador());
                carrera.agregarPosicion(posicion);
            }

            em.persist(carrera);

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

    private Jugador obtenerOCrearJugador(EntityManager em, String nombre, String email,
                                         int puntajeTotal) {
        List<Jugador> jugadores = em.createQuery(
                "select j from Jugador j where j.email = :email", Jugador.class)
                .setParameter("email", email)
                .getResultList();
        Jugador jugador;
        if (jugadores.isEmpty()) {
            jugador = new Jugador(nombre, email, puntajeTotal);
            em.persist(jugador);
        } else {
            jugador = jugadores.get(0);
            jugador.setPuntajeAcumulado(puntajeTotal);
        }
        return jugador;
    }
}
