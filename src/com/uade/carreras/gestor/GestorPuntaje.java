package com.uade.carreras.gestor;

public class GestorPuntaje {

    public int calcularPuntos(int posicion) {
        if (posicion == 1) return 100;
        if (posicion == 2) return 50;
        return 10;
    }
}
