package com.uade.carreras.gestor;

import com.uade.carreras.modelo.Jugador;
import com.uade.carreras.dao.JugadorDAO;

import java.util.ArrayList;

public class GestorJugadores {

    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private JugadorDAO jugadorDAO = new JugadorDAO();

    public Jugador obtenerOCrear(String nombre, String email) {

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getEmail().equals(email)) {
                return jugadores.get(i);
            }
        }

        int puntajeGuardado = jugadorDAO.obtenerPuntaje(email);
        Jugador nuevo = new Jugador(nombre, email, puntajeGuardado);
        jugadores.add(nuevo);
        return nuevo;
    }
}
