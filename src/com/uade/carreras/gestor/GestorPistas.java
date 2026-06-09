package com.uade.carreras.gestor;

import com.uade.carreras.modelo.Pista;

// arma la lista de pistas del juego
public class GestorPistas {

    private Pista[] pistas;

    public GestorPistas() {
        pistas = new Pista[]{
                new Pista(1, "Gran Premio Nacional", 2500),
                new Pista(2, "Gran Premio Carlos Pellegrini", 2400),
                new Pista(3, "Gran Premio Jockey Club", 2000)
        };
    }

    public Pista[] getPistas() {
        return pistas;
    }
}
