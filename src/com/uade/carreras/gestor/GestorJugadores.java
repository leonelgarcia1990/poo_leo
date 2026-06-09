package com.uade.carreras.gestor;

import com.uade.carreras.modelo.Jugador;
import com.uade.carreras.dao.JugadorDAO;

import java.util.ArrayList;

// guarda los jugadores de esta sesion para reusarlos
public class GestorJugadores {

    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private JugadorDAO jugadorDAO = new JugadorDAO();

    public Jugador obtenerOCrear(String nombre, String email) {
        // si el jugador ya jugo en esta sesion devuelvo el mismo
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getEmail().equals(email)) {
                return jugadores.get(i);
            }
        }
        // si es la primera vez, lo creo arrancando con el puntaje que tenia guardado en la base
        int puntajeGuardado = jugadorDAO.obtenerPuntaje(email);
        Jugador nuevo = new Jugador(nombre, email, puntajeGuardado);
        jugadores.add(nuevo);
        return nuevo;
    }
}
