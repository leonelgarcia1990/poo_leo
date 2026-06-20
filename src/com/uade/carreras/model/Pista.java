package com.uade.carreras.model;

public class Pista {

    private int id;
    private String nombre;
    private int distancia;

    public Pista(int id, String nombre, int distancia) {
        this.id = id;
        this.nombre = nombre;
        this.distancia = distancia;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getDistancia() { return distancia; }

    @Override
    public String toString() {
        return nombre + "   (" + distancia + " metros)";
    }
}
