package com.uade.carreras.dto;

// datos del resultado para mostrar al final de la carrera
public class ResultadoCarreraDTO {

    private String nombreGanador;
    private String tipoGanador;
    private String nombreJugador;
    private boolean ganoElJugador;
    private int posicionJugador;
    private int puntajeGanado;
    private int puntajeTotal;

    public ResultadoCarreraDTO(String nombreGanador, String tipoGanador, String nombreJugador,
                               boolean ganoElJugador, int posicionJugador, int puntajeGanado, int puntajeTotal) {
        this.nombreGanador = nombreGanador;
        this.tipoGanador = tipoGanador;
        this.nombreJugador = nombreJugador;
        this.ganoElJugador = ganoElJugador;
        this.posicionJugador = posicionJugador;
        this.puntajeGanado = puntajeGanado;
        this.puntajeTotal = puntajeTotal;
    }

    public String getNombreGanador() { return nombreGanador; }
    public String getTipoGanador() { return tipoGanador; }
    public String getNombreJugador() { return nombreJugador; }
    public boolean ganoElJugador() { return ganoElJugador; }
    public int getPosicionJugador() { return posicionJugador; }
    public int getPuntajeGanado() { return puntajeGanado; }
    public int getPuntajeTotal() { return puntajeTotal; }
}
