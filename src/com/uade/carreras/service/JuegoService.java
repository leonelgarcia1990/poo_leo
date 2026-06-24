package com.uade.carreras.service;

import com.uade.carreras.dao.CaballoDAO;
import com.uade.carreras.dao.CarreraDAO;
import com.uade.carreras.dao.InicializadorBaseDatosDAO;
import com.uade.carreras.dao.JugadorDAO;
import com.uade.carreras.dao.PistaDAO;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.dto.RankingDTO;
import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Carrera;
import com.uade.carreras.model.Pista;

import java.util.List;

/**
 * Service: orquesta los DAOs y la lógica de negocio del juego.
 * Los controllers le hablan a este service y ya no usan los DAOs directamente.
 */
public class JuegoService {

    private final CaballoDAO caballoDAO;
    private final PistaDAO pistaDAO;
    private final JugadorDAO jugadorDAO;
    private final CarreraDAO carreraDAO;
    private final InicializadorBaseDatosDAO inicializadorDAO;

    public JuegoService() {
        this.caballoDAO = new CaballoDAO();
        this.pistaDAO = new PistaDAO();
        this.jugadorDAO = new JugadorDAO();
        this.carreraDAO = new CarreraDAO();
        this.inicializadorDAO = new InicializadorBaseDatosDAO();
        this.inicializadorDAO.inicializar();
    }

    public List<Caballo> listarCaballos() {
        return caballoDAO.listarCaballos();
    }

    public List<Pista> listarPistas() {
        return pistaDAO.listarPistas();
    }

    public int obtenerPuntaje(String email) {
        return jugadorDAO.obtenerPuntaje(email);
    }

    public List<RankingDTO> rankingJugadores() {
        return jugadorDAO.rankingJugadores();
    }

    public List<RankingDTO> rankingCaballos() {
        return caballoDAO.rankingCaballos();
    }

    public void guardarResultado(Carrera carrera, String nombreJugador, String emailJugador,
                                 int puntajeTotal, int pistaId, int caballoElegidoId,
                                 int caballoGanadorId, boolean ganoElJugador,
                                 PosicionDTO[] tablaPosiciones) {
        carreraDAO.guardarResultado(carrera, nombreJugador, emailJugador, puntajeTotal,
                pistaId, caballoElegidoId, caballoGanadorId, ganoElJugador, tablaPosiciones);
    }
}
