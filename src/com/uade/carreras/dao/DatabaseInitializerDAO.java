package com.uade.carreras.dao;

import com.uade.carreras.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// crea las tablas si no existen y carga los caballos y pistas la primera vez
public class DatabaseInitializerDAO {

    public void inicializar() {
        try {
            Connection c = DBConnection.getInstance().getConnection();
            Statement s = c.createStatement();

            s.execute("CREATE TABLE IF NOT EXISTS jugador ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "email VARCHAR(150) NOT NULL, "
                    + "puntaje_acumulado INT NOT NULL DEFAULT 0, "
                    + "UNIQUE (email))");

            s.execute("CREATE TABLE IF NOT EXISTS caballo ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "tipo VARCHAR(20) NOT NULL, "
                    + "velocidad_base INT NOT NULL, "
                    + "resistencia INT NOT NULL)");

            s.execute("CREATE TABLE IF NOT EXISTS pista ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "distancia INT NOT NULL)");

            s.execute("CREATE TABLE IF NOT EXISTS carrera ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "jugador_id INT NOT NULL, "
                    + "pista_id INT NOT NULL, "
                    + "caballo_jugador_id INT NOT NULL, "
                    + "caballo_ganador_id INT NOT NULL, "
                    + "gano_el_jugador BOOLEAN NOT NULL, "
                    + "FOREIGN KEY (jugador_id) REFERENCES jugador(id), "
                    + "FOREIGN KEY (pista_id) REFERENCES pista(id), "
                    + "FOREIGN KEY (caballo_jugador_id) REFERENCES caballo(id), "
                    + "FOREIGN KEY (caballo_ganador_id) REFERENCES caballo(id))");

            s.execute("CREATE TABLE IF NOT EXISTS posicion ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "carrera_id INT NOT NULL, "
                    + "caballo_id INT NOT NULL, "
                    + "posicion INT NOT NULL, "
                    + "puntos INT NOT NULL, "
                    + "es_del_jugador BOOLEAN NOT NULL, "
                    + "FOREIGN KEY (carrera_id) REFERENCES carrera(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (caballo_id) REFERENCES caballo(id), "
                    + "UNIQUE (carrera_id, caballo_id))");

            sembrarCatalogo(c);

            c.close();
        } catch (SQLException e) {
            // si la base no esta disponible el juego igual funciona
            System.err.println("No se pudo inicializar la base de datos: " + e.getMessage());
        }
    }

    // carga los caballos y pistas solo si las tablas estan vacias
    private void sembrarCatalogo(Connection c) throws SQLException {
        if (tablaVacia(c, "caballo")) {
            insertarCaballo(c, 1, "Relámpago", "VELOCISTA", 92, 55);
            insertarCaballo(c, 2, "Tormenta", "RESISTENTE", 68, 92);
            insertarCaballo(c, 3, "Furia", "VELOCISTA", 90, 50);
            insertarCaballo(c, 4, "Centella", "EQUILIBRADO", 80, 80);
        }
        if (tablaVacia(c, "pista")) {
            insertarPista(c, 1, "Gran Premio Nacional", 2500);
            insertarPista(c, 2, "Gran Premio Carlos Pellegrini", 2400);
            insertarPista(c, 3, "Gran Premio Jockey Club", 2000);
        }
    }

    private boolean tablaVacia(Connection c, String tabla) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM " + tabla);
        rs.next();
        return rs.getInt(1) == 0;
    }

    private void insertarCaballo(Connection c, int id, String nombre, String tipo,
                                 int velocidad, int resistencia) throws SQLException {
        String sql = "INSERT INTO caballo (id, nombre, tipo, velocidad_base, resistencia) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setString(3, tipo);
        ps.setInt(4, velocidad);
        ps.setInt(5, resistencia);
        ps.executeUpdate();
    }

    private void insertarPista(Connection c, int id, String nombre, int distancia) throws SQLException {
        String sql = "INSERT INTO pista (id, nombre, distancia) VALUES (?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, distancia);
        ps.executeUpdate();
    }
}
