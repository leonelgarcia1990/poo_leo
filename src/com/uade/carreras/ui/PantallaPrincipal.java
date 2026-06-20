package com.uade.carreras.ui;

import com.uade.carreras.controller.ControladorJuego;

import javax.swing.*;
import java.awt.*;

public class PantallaPrincipal extends JFrame {

    private JButton btnJugar = new JButton("Jugar");
    private ControladorJuego juego;

    public PantallaPrincipal(ControladorJuego juego) {
        this.juego = juego;

        setTitle("Carreras de Caballos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        construirPantalla();
        registrarEventos();

        setSize(1100, 680);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void construirPantalla() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel grupo = new JPanel(new GridLayout(3, 1, 0, 15));

        JLabel titulo = new JLabel("Carreras de Caballos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        grupo.add(titulo);

        JLabel subtitulo = new JLabel("Selecciona tu caballo y gana la carrera", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 20));
        grupo.add(subtitulo);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnJugar.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnJugar.setMargin(new Insets(18, 50, 18, 50));
        panelBoton.add(btnJugar);
        grupo.add(panelBoton);

        panelCentro.add(grupo);
        root.add(panelCentro, BorderLayout.CENTER);

        JPanel espacioArriba = new JPanel();
        espacioArriba.setPreferredSize(new Dimension(0, 230));
        root.add(espacioArriba, BorderLayout.NORTH);

        setContentPane(root);
    }

    private void registrarEventos() {
        btnJugar.addActionListener(e -> {
            PantallaConfiguracion config = new PantallaConfiguracion(juego);
            config.setVisible(true);
            dispose();
        });
    }
}
