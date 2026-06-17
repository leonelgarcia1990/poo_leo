package com.uade.carreras.modelo;

public class CaballoVelocista extends Caballo {

    public CaballoVelocista(int id, String nombre, int velocidadBase, int resistencia) {
        super(id, nombre, velocidadBase, resistencia);
    }

    @Override
    public String getTipo() {
        return "Velocista";
    }

    @Override
    public void avanzar() {
        double factor = 0.4 + 0.6 * (energiaActual / 100.0);
        int random = (int) (Math.random() * 35);
        distanciaRecorrida += (int) (getVelocidadBase() * 1.30 * factor) + random;
        energiaActual = energiaActual - 8;
        if (energiaActual < 0) {
            energiaActual = 0;
        }
    }
}
