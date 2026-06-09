package com.uade.carreras.dto;

// datos de un carril de la carrera (un caballo y cuanto avanzo)
public class CarrilDTO {

    private String nombre;
    private String tipo;
    private int distanciaRecorrida;
    private boolean esDelJugador;

    public CarrilDTO(String nombre, String tipo, int distanciaRecorrida, boolean esDelJugador) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.distanciaRecorrida = distanciaRecorrida;
        this.esDelJugador = esDelJugador;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getDistanciaRecorrida() { return distanciaRecorrida; }
    public boolean esDelJugador() { return esDelJugador; }
}
