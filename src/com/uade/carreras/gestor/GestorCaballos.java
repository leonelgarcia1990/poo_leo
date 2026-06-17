package com.uade.carreras.gestor;

import com.uade.carreras.modelo.Caballo;
import com.uade.carreras.modelo.CaballoVelocista;
import com.uade.carreras.modelo.CaballoResistente;
import com.uade.carreras.modelo.CaballoEquilibrado;

public class GestorCaballos {

    private Caballo[] caballos;

    public GestorCaballos() {
        caballos = new Caballo[]{
                new CaballoVelocista(1, "Relámpago", 92, 55),
                new CaballoResistente(2, "Tormenta", 68, 92),
                new CaballoVelocista(3, "Furia", 90, 50),
                new CaballoEquilibrado(4, "Centella", 80, 80)
        };
    }

    public Caballo[] getCaballos() {
        return caballos;
    }
}
