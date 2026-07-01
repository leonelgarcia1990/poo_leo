package com.uade.carreras.service;

import com.uade.carreras.dao.JugadorDAO;


public class JugadorService {

    private final JugadorDAO jugadorDAO = new JugadorDAO();

    public int obtenerPuntaje(String email) {
        return jugadorDAO.obtenerPuntaje(email);
    }
}
