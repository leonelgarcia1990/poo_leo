package com.uade.carreras.gestor;

// calcula los puntos segun la posicion en la que termino el caballo
public class GestorPuntaje {

    // 1° = 100, 2° = 60, 3° = 30, 4° = 10
    private int[] puntosPorPosicion = {100, 60, 30, 10};

    public int calcularPuntos(int posicion) {
        if (posicion >= 1 && posicion <= puntosPorPosicion.length) {
            return puntosPorPosicion[posicion - 1];
        }
        return 0;
    }
}
