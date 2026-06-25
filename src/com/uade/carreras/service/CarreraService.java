package com.uade.carreras.service;

import com.uade.carreras.dao.CaballoDAO;
import com.uade.carreras.dao.CarreraDAO;
import com.uade.carreras.dao.PistaDAO;
import com.uade.carreras.dto.PosicionDTO;
import com.uade.carreras.model.Caballo;
import com.uade.carreras.model.Carrera;
import com.uade.carreras.model.Pista;

import java.util.List;

/**
 * Service responsable de la carrera: listar las opciones para armarla
 * y persistir el resultado.
 */
public class CarreraService {

    private final CaballoDAO caballoDAO = new CaballoDAO();
    private final PistaDAO pistaDAO = new PistaDAO();
    private final CarreraDAO carreraDAO = new CarreraDAO();

    public List<Caballo> listarCaballos() {
        return caballoDAO.listarCaballos();
    }

    public List<Pista> listarPistas() {
        return pistaDAO.listarPistas();
    }

    public void guardarResultado(Carrera carrera, String nombreJugador, String emailJugador,
                                 int puntajeTotal, int pistaId, int caballoElegidoId,
                                 int caballoGanadorId, boolean ganoElJugador,
                                 PosicionDTO[] tablaPosiciones) {
        carreraDAO.guardarResultado(carrera, nombreJugador, emailJugador, puntajeTotal,
                pistaId, caballoElegidoId, caballoGanadorId, ganoElJugador, tablaPosiciones);
    }
}
