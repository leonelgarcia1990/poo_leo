package com.uade.carreras.dao;

import com.uade.carreras.db.DBConnection;
import com.uade.carreras.dto.RankingDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    public int obtenerPuntaje(String email) {
        try {
            Connection c = DBConnection.getInstance().getConnection();
            PreparedStatement ps = c.prepareStatement(
                    "SELECT puntaje_acumulado FROM jugador WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            int puntaje = 0;
            if (rs.next()) {
                puntaje = rs.getInt(1);
            }
            c.close();
            return puntaje;
        } catch (SQLException e) {
            System.err.println("No se pudo leer el puntaje del jugador: " + e.getMessage());
            return 0;
        }
    }

    public List<RankingDTO> rankingJugadores() {
        List<RankingDTO> ranking = new ArrayList<>();
        String sql = "SELECT nombre, email, puntaje_acumulado "
                + "FROM jugador ORDER BY puntaje_acumulado DESC, nombre";
        try {
            Connection c = DBConnection.getInstance().getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            int pos = 1;
            while (rs.next()) {
                ranking.add(new RankingDTO(pos, rs.getString("nombre"),
                        rs.getString("email"), rs.getInt("puntaje_acumulado")));
                pos++;
            }
            c.close();
        } catch (SQLException e) {
            System.err.println("No se pudo leer el ranking de jugadores: " + e.getMessage());
        }
        return ranking;
    }
}
