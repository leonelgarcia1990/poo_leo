package com.uade.carreras.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// clase para conectarse a la base de datos. siempre uso la misma instancia.
public class DBConnection {

    private static DBConnection instance;

    // datos de conexion a mysql (cambiar usuario y clave si hace falta)
    private static final String URL =
            "jdbc:mysql://localhost:3306/carreras"
            + "?createDatabaseIfNotExist=true"
            + "&useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "adminroot";

    // constructor privado para que nadie pueda hacer new desde afuera
    private DBConnection() { }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // abre una conexion nueva con la base
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
