package com.uade.carreras.vista;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.dto.RankingDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PantallaRanking extends JFrame {

    private ControladorJuego juego;
    private JButton btnVolver = new JButton("Volver a la tabla");

    public PantallaRanking(ControladorJuego juego) {
        this.juego = juego;

        setTitle("Ranking histórico");

        setSize(1100, 680);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        construirPantalla();
        registrarEventos();
    }

    private void construirPantalla() {
        JPanel root = new JPanel(new BorderLayout(10, 20));
        root.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel("🏆 Ranking histórico", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        root.add(titulo, BorderLayout.NORTH);

        JPanel panelTablas = new JPanel(new GridLayout(1, 2, 20, 0));
        panelTablas.add(crearPanelRanking("Jugadores",
                new String[]{"Pos.", "Jugador", "Email", "Puntos"},
                juego.getRankingJugadores()));
        panelTablas.add(crearPanelRanking("Caballos",
                new String[]{"Pos.", "Caballo", "Tipo", "Puntos"},
                juego.getRankingCaballos()));
        root.add(panelTablas, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnVolver.setMargin(new Insets(12, 35, 12, 35));
        panelBoton.add(btnVolver);
        root.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel crearPanelRanking(String subtitulo, String[] columnas, RankingDTO[] filas) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));

        JLabel lbl = new JLabel(subtitulo, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(lbl, BorderLayout.NORTH);

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < filas.length; i++) {
            RankingDTO r = filas[i];
            modelo.addRow(new Object[]{r.getPosicion() + "°", r.getNombre(), r.getDetalle(), r.getPuntaje()});
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(36);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        tabla.setEnabled(false);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void registrarEventos() {
        btnVolver.addActionListener(e -> {
            new PantallaPosiciones(juego).setVisible(true);
            dispose();
        });
    }
}
