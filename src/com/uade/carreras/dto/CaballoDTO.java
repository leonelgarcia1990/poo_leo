package com.uade.carreras.dto;

// datos del caballo para mostrar en la pantalla
public class CaballoDTO {

    private String nombre;
    private String tipo;
    private int velocidad;
    private int resistencia;
    private int energia;

    public CaballoDTO(String nombre, String tipo, int velocidad, int resistencia, int energia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.velocidad = velocidad;
        this.resistencia = resistencia;
        this.energia = energia;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getVelocidad() { return velocidad; }
    public int getResistencia() { return resistencia; }
    public int getEnergia() { return energia; }

    @Override
    public String toString() {
        return nombre + " [" + tipo + "]"
                + "   (Velocidad: " + velocidad
                + " | Resistencia: " + resistencia
                + " | Energía: " + energia + ")";
    }
}
