package com.uade.carreras.service;

import com.uade.carreras.dao.JugadorDAO;

/**
 * Service responsable del jugador: consulta de puntaje.
 */
public class JugadorService {

    private final JugadorDAO jugadorDAO = new JugadorDAO();

    public int obtenerPuntaje(String email) {
        return jugadorDAO.obtenerPuntaje(email);
    }
}
