package com.uade.carreras.service;

import com.uade.carreras.model.Caballo;

public class AvanceResistente implements EstrategiaAvance {

    @Override
    public void avanzar(Caballo caballo) {
        double factor = 0.4 + 0.6 * (caballo.getEnergiaActual() / 100.0);
        int random = (int) (Math.random() * 35);
        caballo.avanzarDistancia((int) (caballo.getVelocidadBase() * 1.15 * factor) + random);
        caballo.reducirEnergia(2);
    }
}
