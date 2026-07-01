package com.uade.carreras.service;

import com.uade.carreras.dao.CaballoDAO;
import com.uade.carreras.dao.JugadorDAO;
import com.uade.carreras.dto.RankingDTO;

import java.util.List;



public class RankingService {

    private final JugadorDAO jugadorDAO = new JugadorDAO();
    private final CaballoDAO caballoDAO = new CaballoDAO();

    public List<RankingDTO> rankingJugadores() {
        return jugadorDAO.rankingJugadores();
    }

    public List<RankingDTO> rankingCaballos() {
        return caballoDAO.rankingCaballos();
    }
}
