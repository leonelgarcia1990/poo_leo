package com.uade.carreras.modelo;

public abstract class Caballo {

    private int id;
    private String nombre;
    private int velocidadBase;
    private int resistencia;

    protected int energiaActual;
    protected double distanciaRecorrida;

    public Caballo(int id, String nombre, int velocidadBase, int resistencia) {
        this.id = id;
        this.nombre = nombre;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
        this.energiaActual = 100;
        this.distanciaRecorrida = 0;
    }

    public static Caballo crear(int id, String nombre, String tipo, int velocidadBase, int resistencia) {
        if (tipo.equalsIgnoreCase("VELOCISTA")) {
            return new CaballoVelocista(id, nombre, velocidadBase, resistencia);
        }
        if (tipo.equalsIgnoreCase("RESISTENTE")) {
            return new CaballoResistente(id, nombre, velocidadBase, resistencia);
        }
        return new CaballoEquilibrado(id, nombre, velocidadBase, resistencia);
    }

    public abstract String getTipo();
    public abstract void avanzar();

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getVelocidadBase() { return velocidadBase; }
    public int getResistencia() { return resistencia; }
    public int getEnergiaActual() { return energiaActual; }
    public double getDistanciaRecorrida() { return distanciaRecorrida; }

    @Override
    public String toString() {
        return nombre + " [" + getTipo() + "]"
                + "   (Velocidad: " + velocidadBase
                + " | Resistencia: " + resistencia
                + " | Energía: " + energiaActual + ")";
    }
}
