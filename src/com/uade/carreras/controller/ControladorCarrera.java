package com.uade.carreras.controller;

import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Carrera;
import com.uade.carreras.model.Jugador;
import com.uade.carreras.model.Pista;
import com.uade.carreras.service.JuegoService;
import com.uade.carreras.dto.CarrilDTO;
import com.uade.carreras.dto.ResultadoCarreraDTO;
import com.uade.carreras.dto.PosicionDTO;

public class ControladorCarrera {

    private Jugador jugador;
    private Pista pista;
    private Carrera carrera;
    private JuegoService juegoService;

    private boolean puntajeAplicado = false;
    private int posicionJugador = 0;
    private int puntajeGanado = 0;

    public ControladorCarrera(JuegoService juegoService, Jugador jugador, Caballo[] caballos,
                              Pista pista, Caballo caballoJugador) {
        this.juegoService = juegoService;
        this.jugador = jugador;
        this.pista = pista;
        this.carrera = new Carrera(pista.getDistancia(), caballos, caballoJugador);
    }

    public void avanzarUnPaso() {
        carrera.paso();
        if (carrera.isCarreraFinalizada() && !puntajeAplicado) {
            aplicarPuntaje();
            puntajeAplicado = true;
            guardarEnBaseDeDatos();
        }
    }

    private void aplicarPuntaje() {
        posicionJugador = carrera.obtenerPosicion(carrera.getCaballoJugador());
        puntajeGanado = carrera.calcularPuntos(posicionJugador);
        jugador.agregarPuntaje(puntajeGanado);
    }

    private void guardarEnBaseDeDatos() {
        try {
            Caballo ganador = carrera.getGanador();
            juegoService.guardarResultado(
                    carrera,
                    jugador.getNombre(),
                    jugador.getEmail(),
                    jugador.getPuntajeAcumulado(),
                    pista.getId(),
                    carrera.getCaballoJugador().getId(),
                    ganador.getId(),
                    carrera.ganoElJugador(),
                    getTablaPosiciones());
        } catch (Exception e) {
            System.err.println("No se pudo guardar la carrera en la BD: " + e.getMessage());
        }
    }

    public boolean termino() {
        return carrera.isCarreraFinalizada();
    }

    public String getNombrePista() {
        return pista.getNombre();
    }

    public int getDistanciaTotal() {
        return (int) carrera.getDistanciaTotal();
    }

    public CarrilDTO[] getCarriles() {
        Caballo[] cs = carrera.getCaballos();
        CarrilDTO[] carriles = new CarrilDTO[cs.length];
        for (int i = 0; i < cs.length; i++) {
            Caballo c = cs[i];
            int distancia = (int) c.getDistanciaRecorrida();
            boolean esDelJugador = carrera.esDelJugador(c);
            carriles[i] = new CarrilDTO(c.getNombre(), c.getTipo(), distancia, esDelJugador);
        }
        return carriles;
    }

    public PosicionDTO[] getTablaPosiciones() {
        Caballo[] orden = carrera.getCaballosOrdenados();
        PosicionDTO[] tabla = new PosicionDTO[orden.length];
        for (int i = 0; i < orden.length; i++) {
            Caballo c = orden[i];
            int posicion = i + 1;
            int puntos = carrera.calcularPuntos(posicion);
            tabla[i] = new PosicionDTO(c.getId(), posicion, c.getNombre(), c.getTipo(), puntos, carrera.esDelJugador(c));
        }
        return tabla;
    }

    public ResultadoCarreraDTO getResultado() {
        Caballo ganador = carrera.getGanador();
        boolean ganoElJugador = carrera.ganoElJugador();
        return new ResultadoCarreraDTO(
                ganador.getNombre(),
                ganador.getTipo(),
                jugador.getNombre(),
                ganoElJugador,
                posicionJugador,
                puntajeGanado,
                jugador.getPuntajeAcumulado());
    }
}
