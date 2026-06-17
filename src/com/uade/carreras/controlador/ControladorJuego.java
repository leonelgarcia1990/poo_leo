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

// controlador principal del juego. lo usan todas las pantallas.
public class ControladorJuego {
     //atributos
    private Caballo[] caballos;
    private Pista[] pistas;
    private GestorJugadores gestorJugadores = new GestorJugadores();
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private CaballoDAO caballoDAO = new CaballoDAO();
    private ControladorCarrera ultimaCarrera;
    private String nombreJugadorActual;
    private String emailJugadorActual;

    //el constructor se encarga de inicializar la base de datos 
    // y cargar los caballos y pistas disponibles
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
        // uso el mismo jugador (por email) para que el puntaje se vaya sumando
        Jugador jugador = gestorJugadores.obtenerOCrear(config.getNombre(), config.getEmail());
        // me guardo el nombre y email para no volver a pedirlos al "Seguir jugando"
        nombreJugadorActual = config.getNombre();
        emailJugadorActual = config.getEmail();
        // caballos nuevos para cada carrera (arrancan en 0 y con energia 100)
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

    // tabla de posiciones de la ultima carrera
    public PosicionDTO[] getTablaPosiciones() {
        return ultimaCarrera.getTablaPosiciones();
    }

    // rankings historicos leidos de la base
    public RankingDTO[] getRankingJugadores() {
        List<RankingDTO> ranking = jugadorDAO.rankingJugadores();
        return ranking.toArray(new RankingDTO[0]);
    }

    public RankingDTO[] getRankingCaballos() {
        List<RankingDTO> ranking = caballoDAO.rankingCaballos();
        return ranking.toArray(new RankingDTO[0]);
    }
}
