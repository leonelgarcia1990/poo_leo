package com.uade.carreras.dao;

import com.uade.carreras.db.DBConnection;
import com.uade.carreras.dto.PosicionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// guarda en la base el resultado de una carrera
public class CarreraDAO {

    public void guardarResultado(String nombreJugador, String emailJugador, int puntajeTotal,
                                 int pistaId, int caballoElegidoId,
                                 int caballoGanadorId, boolean ganoElJugador,
                                 PosicionDTO[] tablaPosiciones) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            int jugadorId = obtenerOCrearJugador(conn, nombreJugador, emailJugador, puntajeTotal);
            int carreraId = insertarCarrera(conn, jugadorId, pistaId,
                    caballoElegidoId, caballoGanadorId, ganoElJugador);
            insertarPosiciones(conn, carreraId, tablaPosiciones);
        } finally {
            conn.close();
        }
    }

    // si el jugador ya existe le actualizo el puntaje, si no lo creo
    private int obtenerOCrearJugador(Connection conn, String nombre, String email,
                                     int puntajeTotal) throws SQLException {
        PreparedStatement buscar = conn.prepareStatement("SELECT id FROM jugador WHERE email = ?");
        buscar.setString(1, email);
        ResultSet rs = buscar.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            PreparedStatement actualizar = conn.prepareStatement(
                    "UPDATE jugador SET puntaje_acumulado = ? WHERE id = ?");
            actualizar.setInt(1, puntajeTotal);
            actualizar.setInt(2, id);
            actualizar.executeUpdate();
            return id;
        }
        PreparedStatement insertar = conn.prepareStatement(
                "INSERT INTO jugador (nombre, email, puntaje_acumulado) VALUES (?, ?, ?)");
        insertar.setString(1, nombre);
        insertar.setString(2, email);
        insertar.setInt(3, puntajeTotal);
        insertar.executeUpdate();
        return ultimoId(conn, "jugador");
    }

    private int insertarCarrera(Connection conn, int jugadorId, int pistaId,
                                int caballoElegidoId, int ganadorId,
                                boolean ganoElJugador) throws SQLException {
        String sql = "INSERT INTO carrera "
                + "(jugador_id, pista_id, caballo_jugador_id, caballo_ganador_id, gano_el_jugador) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, jugadorId);
        ps.setInt(2, pistaId);
        ps.setInt(3, caballoElegidoId);
        ps.setInt(4, ganadorId);
        ps.setBoolean(5, ganoElJugador);
        ps.executeUpdate();
        return ultimoId(conn, "carrera");
    }

    // inserto una fila por cada caballo de la tabla de posiciones
    private void insertarPosiciones(Connection conn, int carreraId,
                                    PosicionDTO[] tabla) throws SQLException {
        String sql = "INSERT INTO posicion "
                + "(carrera_id, caballo_id, posicion, puntos, es_del_jugador) "
                + "VALUES (?, ?, ?, ?, ?)";
        for (int i = 0; i < tabla.length; i++) {
            PosicionDTO p = tabla[i];
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, carreraId);
            ps.setInt(2, p.getCaballoId());
            ps.setInt(3, p.getPosicion());
            ps.setInt(4, p.getPuntos());
            ps.setBoolean(5, p.esDelJugador());
            ps.executeUpdate();
        }
    }

    // busca el ultimo id que se genero en una tabla
    private int ultimoId(Connection conn, String tabla) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT MAX(id) FROM " + tabla);
        rs.next();
        return rs.getInt(1);
    }
}
