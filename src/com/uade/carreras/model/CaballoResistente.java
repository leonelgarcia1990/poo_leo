package com.uade.carreras.model;

public class CaballoResistente extends Caballo {

    public CaballoResistente(int id, String nombre, int velocidadBase, int resistencia) {
        super(id, nombre, velocidadBase, resistencia);
    }

    @Override
    public String getTipo() {
        return "Resistente";
    }

    @Override
    public void avanzar() {
        double factor = 0.4 + 0.6 * (energiaActual / 100.0);
        int random = (int) (Math.random() * 35);
        distanciaRecorrida += (int) (getVelocidadBase() * 1.15 * factor) + random;
        energiaActual = energiaActual - 2;
        if (energiaActual < 0) {
            energiaActual = 0;
        }
    }
}
