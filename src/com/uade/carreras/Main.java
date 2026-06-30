package com.uade.carreras;

import com.uade.carreras.controller.ControladorJuego;
import com.uade.carreras.dao.InicializadorBaseDatosDAO;
import com.uade.carreras.ui.PantallaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Inicialización de la base de datos al arrancar (fuera de los services).
        new InicializadorBaseDatosDAO().inicializar();

        SwingUtilities.invokeLater(() -> {
            ControladorJuego juego = new ControladorJuego();
            PantallaPrincipal frame = new PantallaPrincipal(juego);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
} 
