package com.uade.carreras.controlador;

import com.uade.carreras.modelo.Caballo;
import com.uade.carreras.modelo.Jugador;
import com.uade.carreras.modelo.Pista;

import com.uade.carreras.gestor.GestorCaballos;
import com.uade.carreras.gestor.GestorPistas;
import com.uade.carreras.gestor.GestorJugadores;

import com.uade.carreras.dao.JugadorDAO;
import com.uade.carreras.dao.CaballoDAO;
import com.uade.carreras.dao.InicializadorBaseDatosDAO;

import com.uade.carreras.dto.CaballoDTO;
import com.uade.carreras.dto.PistaDTO;
import com.uade.carreras.dto.ConfiguracionCarreraDTO;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.dto.RankingDTO;

import java.util.List;

public class ControladorJuego {
    private Caballo[] caballos;
    private Pista[] pistas;
    private GestorJugadores gestorJugadores = new GestorJugadores();
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private CaballoDAO caballoDAO = new CaballoDAO();
    private ControladorCarrera ultimaCarrera;
    private String nombreJugadorActual;
    private String emailJugadorActual;

    public ControladorJuego() {
        new InicializadorBaseDatosDAO().inicializar();
        this.caballos = new GestorCaballos().getCaballos();
        this.pistas = new GestorPistas().getPistas();
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
        Jugador jugador = gestorJugadores.obtenerOCrear(config.getNombre(), config.getEmail());
        nombreJugadorActual = config.getNombre();
        emailJugadorActual = config.getEmail();
        Caballo[] caballosCarrera = new GestorCaballos().getCaballos();
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
