package com.uade.carreras.service;

import com.uade.carreras.model.Caballo;

public interface EstrategiaAvance {

    void avanzar(Caballo caballo);

    static EstrategiaAvance para(String tipo) {
        if (tipo.equalsIgnoreCase("VELOCISTA")) {
            return new AvanceVelocista();
        }
        if (tipo.equalsIgnoreCase("RESISTENTE")) {
            return new AvanceResistente();
        }
        return new AvanceEquilibrado();
    }
}
