package com.uade.carreras.dto;

// una fila de la tabla de posiciones
public class PosicionDTO {

    private int caballoId;
    private int posicion;
    private String nombre;
    private String tipo;
    private int puntos;
    private boolean esDelJugador;

    public PosicionDTO(int caballoId, int posicion, String nombre, String tipo, int puntos, boolean esDelJugador) {
        this.caballoId = caballoId;
        this.posicion = posicion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntos = puntos;
        this.esDelJugador = esDelJugador;
    }

    public int getCaballoId() { return caballoId; }
    public int getPosicion() { return posicion; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPuntos() { return puntos; }
    public boolean esDelJugador() { return esDelJugador; }
}
