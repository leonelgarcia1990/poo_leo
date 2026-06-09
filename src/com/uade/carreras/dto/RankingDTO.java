package com.uade.carreras.dto;

// una fila de un ranking (jugadores o caballos)
public class RankingDTO {

    private int posicion;
    private String nombre;
    private String detalle;   // email del jugador o tipo del caballo
    private int puntaje;

    public RankingDTO(int posicion, String nombre, String detalle, int puntaje) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.detalle = detalle;
        this.puntaje = puntaje;
    }

    public int getPosicion() { return posicion; }
    public String getNombre() { return nombre; }
    public String getDetalle() { return detalle; }
    public int getPuntaje() { return puntaje; }
}
