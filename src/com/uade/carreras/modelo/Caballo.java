package com.uade.carreras.modelo;

// clase abstracta. cada tipo de caballo hereda de esta.
public abstract class Caballo {

    private int id;
    private String nombre;
    private int velocidadBase;
    private int resistencia;
    // estos dos van cambiando durante la carrera, por eso son protected
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

    // cada tipo de caballo dice de que tipo es y como avanza
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
