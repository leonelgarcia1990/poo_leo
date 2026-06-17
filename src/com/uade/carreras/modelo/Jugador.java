package com.uade.carreras.modelo;

public class Jugador {

    private String nombre;
    private String email;
    private int puntajeAcumulado;

    public Jugador(String nombre, String email) {
        this(nombre, email, 0);
    }

    public Jugador(String nombre, String email, int puntajeInicial) {
        this.nombre = nombre;
        this.email = email;
        this.puntajeAcumulado = puntajeInicial;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getPuntajeAcumulado() { return puntajeAcumulado; }

    public void agregarPuntaje(int puntos) {
        this.puntajeAcumulado += puntos;
    }
}
