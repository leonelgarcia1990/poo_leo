package com.uade.carreras.modelo;

public class Carrera {

    private double distanciaTotal;
    private Caballo[] caballos;
    private Caballo caballoJugador;
    private boolean carreraFinalizada;
    private Caballo ganador;

    public Carrera(double distanciaTotal, Caballo[] caballos, Caballo caballoJugador) {
        this.distanciaTotal = distanciaTotal;
        this.caballos = caballos;
        this.caballoJugador = caballoJugador;
        this.carreraFinalizada = false;
        this.ganador = null;
    }

    public double getDistanciaTotal() { return distanciaTotal; }
    public Caballo[] getCaballos() { return caballos; }
    public Caballo getCaballoJugador() { return caballoJugador; }
    public boolean isCarreraFinalizada() { return carreraFinalizada; }
    public Caballo getGanador() { return ganador; }

    public void paso() {
        if (carreraFinalizada) {
            return;
        }
        for (int i = 0; i < caballos.length; i++) {
            caballos[i].avanzar();
        }

        if (ganador == null) {
            this.ganador = obtenerGanador();
        }

        if (todosLlegaron()) {
            this.carreraFinalizada = true;
        }
    }

    private boolean todosLlegaron() {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getDistanciaRecorrida() < distanciaTotal) {
                return false;
            }
        }
        return true;
    }

    public boolean esDelJugador(Caballo caballo) {
        return caballo == caballoJugador;
    }

    public boolean ganoElJugador() {
        return ganador == caballoJugador;
    }

    private Caballo obtenerGanador() {
        Caballo mejor = null;
        for (int i = 0; i < caballos.length; i++) {
            Caballo c = caballos[i];
            if (c.getDistanciaRecorrida() >= distanciaTotal) {
                if (mejor == null || c.getDistanciaRecorrida() > mejor.getDistanciaRecorrida()) {
                    mejor = c;
                }
            }
        }
        return mejor;
    }

    public int calcularPuntos(int posicion) {
        if (posicion == 1) return 100;
        if (posicion == 2) return 50;
        return 10;
    }

    public int obtenerPosicion(Caballo objetivo) {
        int posicion = 1;
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getDistanciaRecorrida() > objetivo.getDistanciaRecorrida()) {
                posicion++;
            }
        }
        return posicion;
    }

    public Caballo[] getCaballosOrdenados() {

        Caballo[] orden = new Caballo[caballos.length];
        for (int i = 0; i < caballos.length; i++) {
            orden[i] = caballos[i];
        }
        for (int i = 0; i < orden.length; i++) {
            for (int j = i + 1; j < orden.length; j++) {
                if (orden[j].getDistanciaRecorrida() > orden[i].getDistanciaRecorrida()) {
                    Caballo tmp = orden[i];
                    orden[i] = orden[j];
                    orden[j] = tmp;
                }
            }
        }
        return orden;
    }
}
