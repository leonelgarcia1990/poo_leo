package com.uade.carreras.dto;

public class PistaDTO {

    private String nombre;
    private int distancia;

    public PistaDTO(String nombre, int distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }

    public String getNombre() { return nombre; }
    public int getDistancia() { return distancia; }

    @Override
    public String toString() {
        return nombre + "   (" + distancia + " metros)";
    }
}
