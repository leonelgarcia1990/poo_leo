package com.uade.carreras.controlador;

import com.uade.carreras.modelo.Caballo;
import com.uade.carreras.modelo.Carrera;
import com.uade.carreras.modelo.Jugador;
import com.uade.carreras.modelo.Pista;
import com.uade.carreras.gestor.GestorPuntaje;
import com.uade.carreras.dao.CarreraDAO;
import com.uade.carreras.dto.CarrilDTO;
import com.uade.carreras.dto.ResultadoCarreraDTO;
import com.uade.carreras.dto.PosicionDTO;

public class ControladorCarrera {

    private Jugador jugador;
    private Pista pista;
    private Carrera carrera;
    private GestorPuntaje gestorPuntaje = new GestorPuntaje();
    private CarreraDAO carreraDAO = new CarreraDAO();

    private boolean puntajeAplicado = false;
    private int posicionJugador = 0;
    private int puntajeGanado = 0;

    public ControladorCarrera(Jugador jugador, Caballo[] caballos, Pista pista, Caballo caballoJugador) {
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
        puntajeGanado = gestorPuntaje.calcularPuntos(posicionJugador);
        jugador.agregarPuntaje(puntajeGanado);
    }

    private void guardarEnBaseDeDatos() {
        try {
            Caballo ganador = carrera.getGanador();
            carreraDAO.guardarResultado(
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
            int puntos = gestorPuntaje.calcularPuntos(posicion);
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
