package com.uade.carreras.controller;

import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Jugador;
import com.uade.carreras.model.Pista;

import com.uade.carreras.dao.JugadorDAO;
import com.uade.carreras.dao.CaballoDAO;
import com.uade.carreras.dao.PistaDAO;
import com.uade.carreras.dao.InicializadorBaseDatosDAO;

import com.uade.carreras.entity.CaballoEntity;
import com.uade.carreras.entity.PistaEntity;

import com.uade.carreras.dto.CaballoDTO;
import com.uade.carreras.dto.PistaDTO;
import com.uade.carreras.dto.ConfiguracionCarreraDTO;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.dto.RankingDTO;

import java.util.ArrayList;
import java.util.List;

public class ControladorJuego {
    private Caballo[] caballos;
    private Pista[] pistas;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private CaballoDAO caballoDAO = new CaballoDAO();
    private PistaDAO pistaDAO = new PistaDAO();
    private ControladorCarrera ultimaCarrera;
    private String nombreJugadorActual;
    private String emailJugadorActual;

    public ControladorJuego() {
        new InicializadorBaseDatosDAO().inicializar();
        this.caballos = cargarCaballos();
        this.pistas = cargarPistas();
    }

    private Caballo[] cargarCaballos() {
        List<CaballoEntity> entidades = caballoDAO.listarCaballos();
        Caballo[] resultado = new Caballo[entidades.size()];
        for (int i = 0; i < entidades.size(); i++) {
            CaballoEntity e = entidades.get(i);
            resultado[i] = Caballo.crear(e.getId(), e.getNombre(), e.getTipo(),
                    e.getVelocidadBase(), e.getResistencia());
        }
        return resultado;
    }

    private Pista[] cargarPistas() {
        List<PistaEntity> entidades = pistaDAO.listarPistas();
        Pista[] resultado = new Pista[entidades.size()];
        for (int i = 0; i < entidades.size(); i++) {
            PistaEntity e = entidades.get(i);
            resultado[i] = new Pista(e.getId(), e.getNombre(), e.getDistancia());
        }
        return resultado;
    }

    private Jugador obtenerOCrearJugador(String nombre, String email) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getEmail().equals(email)) {
                return jugadores.get(i);
            }
        }
        int puntajeGuardado = jugadorDAO.obtenerPuntaje(email);
        Jugador nuevo = new Jugador(nombre, email, puntajeGuardado);
        jugadores.add(nuevo);
        return nuevo;
    }

    public CaballoDTO[] getCaballosDisponibles() {
        CaballoDTO[] dtos = new CaballoDTO[caballos.length];
        for (int i = 0; i < caballos.length; i++) {
            Caballo c = caballos[i];
            dtos[i] = new CaballoDTO(c.getNombre(), c.getTipo(),
                    c.getVelocidadBase(), c.getResistencia(), c.getEnergiaActual());
        }
        return dtos;
    }

    public PistaDTO[] getPistasDisponibles() {
        PistaDTO[] dtos = new PistaDTO[pistas.length];
        for (int i = 0; i < pistas.length; i++) {
            dtos[i] = new PistaDTO(pistas[i].getNombre(), pistas[i].getDistancia());
        }
        return dtos;
    }

    public ControladorCarrera iniciarCarrera(ConfiguracionCarreraDTO config) {
        Jugador jugador = obtenerOCrearJugador(config.getNombre(), config.getEmail());
        nombreJugadorActual = config.getNombre();
        emailJugadorActual = config.getEmail();
        Caballo[] caballosCarrera = cargarCaballos();
        Caballo caballoElegido = caballosCarrera[config.getIndiceCaballo()];
        Pista pistaElegida = pistas[config.getIndicePista()];
        ultimaCarrera = new ControladorCarrera(jugador, caballosCarrera, pistaElegida, caballoElegido);
        return ultimaCarrera;
    }

    public boolean hayJugador() {
        return nombreJugadorActual != null;
    }

    public String getNombreJugador() {
        return nombreJugadorActual;
    }

    public String getEmailJugador() {
        return emailJugadorActual;
    }

    public PosicionDTO[] getTablaPosiciones() {
        return ultimaCarrera.getTablaPosiciones();
    }

    public RankingDTO[] getRankingJugadores() {
        List<RankingDTO> ranking = jugadorDAO.rankingJugadores();
        return ranking.toArray(new RankingDTO[0]);
    }

    public RankingDTO[] getRankingCaballos() {
        List<RankingDTO> ranking = caballoDAO.rankingCaballos();
        return ranking.toArray(new RankingDTO[0]);
    }
}
