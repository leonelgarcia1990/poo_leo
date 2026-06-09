package com.uade.carreras;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.vista.PantallaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // un solo controlador para toda la sesion asi se acumula el puntaje
            ControladorJuego juego = new ControladorJuego();
            PantallaPrincipal frame = new PantallaPrincipal(juego);
            frame.setVisible(true);
        });
    }
}
