package com.uade.carreras;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.vista.PantallaPrincipal;

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
