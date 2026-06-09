package com.uade.carreras.gestor;

// calcula los puntos segun la posicion en la que termino el caballo
public class GestorPuntaje {

    // 1° = 100, 2° = 60, resto =10
   

    public int calcularPuntos(int posicion) {
        if (posicion == 1) return 100;
        if (posicion == 2) return 50;
        return 10; // participa
    }
}

