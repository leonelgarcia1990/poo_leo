package com.uade.carreras.dao;

import com.uade.carreras.config.JPAUtil;
import com.uade.carreras.dto.RankingDTO;
import com.uade.carreras.model.Jugador;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    public int obtenerPuntaje(String email) {
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            List<Jugador> jugadores = em.createQuery(
                    "select j from Jugador j where j.email = :email", Jugador.class)
                    .setParameter("email", email)
                    .getResultList();
            return jugadores.isEmpty() ? 0 : jugadores.get(0).getPuntajeAcumulado();
        } catch (Exception e) {
            System.err.println("No se pudo leer el puntaje del jugador: " + e.getMessage());
            return 0;
        } finally {
            em.close();
        }
    }

    public List<RankingDTO> rankingJugadores() {
        List<RankingDTO> ranking = new ArrayList<>();
        EntityManager em = JPAUtil.getInstance().crearEntityManager();
        try {
            List<Jugador> jugadores = em.createQuery(
                    "select j from Jugador j order by j.puntajeAcumulado desc, j.nombre",
                    Jugador.class)
                    .getResultList();
            int pos = 1;
            for (Jugador j : jugadores) {
                ranking.add(new RankingDTO(pos, j.getNombre(), j.getEmail(), j.getPuntajeAcumulado()));
                pos++;
            }
        } catch (Exception e) {
            System.err.println("No se pudo leer el ranking de jugadores: " + e.getMessage());
        } finally {
            em.close();
        }
        return ranking;
    }
}
