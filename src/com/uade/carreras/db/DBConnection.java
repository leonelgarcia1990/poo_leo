package com.uade.carreras.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private static final String URL =
            "jdbc:mysql://localhost:3306/carreras"
            + "?createDatabaseIfNotExist=true"
            + "&useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "adminroot";

    private DBConnection() { }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
