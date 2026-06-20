package com.uade.carreras;

import com.uade.carreras.controller.ControladorJuego;
import com.uade.carreras.ui.PantallaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorJuego juego = new ControladorJuego();
            PantallaPrincipal frame = new PantallaPrincipal(juego);
            frame.setVisible(true);
        });
    }
}
