package com.uade.carreras.modelo;

public class CaballoEquilibrado extends Caballo {

    public CaballoEquilibrado(int id, String nombre, int velocidadBase, int resistencia) {
        super(id, nombre, velocidadBase, resistencia);
    }

    @Override
    public String getTipo() {
        return "Equilibrado";
    }

    // ni muy rapido ni muy lento, gasta energia medio
    @Override
    public void avanzar() {
        double factor = 0.4 + 0.6 * (energiaActual / 100.0);
        int random = (int) (Math.random() * 25);
        distanciaRecorrida += (int) (getVelocidadBase() * 1.20 * factor) + random;
        energiaActual = energiaActual - 3;
        if (energiaActual < 0) {
            energiaActual = 0;
        }
    }
}
