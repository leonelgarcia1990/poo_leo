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

public class CaballoDAO {

    public List<RankingDTO> rankingCaballos() {
        List<RankingDTO> ranking = new ArrayList<>();
        try {
            Connection c = DBConnection.getInstance().getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT id, nombre, tipo FROM caballo");

            while (rs.next()) {
                int total = totalPuntos(c, rs.getInt("id"));
                ranking.add(new RankingDTO(0, rs.getString("nombre"), rs.getString("tipo"), total));
            }
            c.close();
        } catch (SQLException e) {
            System.err.println("No se pudo leer el ranking de caballos: " + e.getMessage());
        }
        ordenarPorPuntos(ranking);
        return ranking;
    }

    private int totalPuntos(Connection c, int caballoId) throws SQLException {
        int total = 0;
        PreparedStatement ps = c.prepareStatement("SELECT puntos FROM posicion WHERE caballo_id = ?");
        ps.setInt(1, caballoId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            total += rs.getInt("puntos");
        }
        return total;
    }

    private void ordenarPorPuntos(List<RankingDTO> ranking) {
        for (int i = 0; i < ranking.size(); i++) {
            for (int j = i + 1; j < ranking.size(); j++) {
                if (ranking.get(j).getPuntaje() > ranking.get(i).getPuntaje()) {
                    RankingDTO tmp = ranking.get(i);
                    ranking.set(i, ranking.get(j));
                    ranking.set(j, tmp);
                }
            }
        }
        for (int i = 0; i < ranking.size(); i++) {
            RankingDTO r = ranking.get(i);
            ranking.set(i, new RankingDTO(i + 1, r.getNombre(), r.getDetalle(), r.getPuntaje()));
        }
    }
}
