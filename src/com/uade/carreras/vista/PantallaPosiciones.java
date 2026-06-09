package com.uade.carreras.vista;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.dto.PosicionDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PantallaPosiciones extends JFrame {

    private ControladorJuego juego;

    private JButton btnSeguir = new JButton("Seguir jugando");
    private JButton btnRanking = new JButton("Ver ranking");
    private JButton btnTerminar = new JButton("Terminar");

    public PantallaPosiciones(ControladorJuego juego) {
        this.juego = juego;

        setTitle("Tabla de posiciones");
        // tamaño fijo que entra en cualquier laptop, incluso las de 13 pulgadas
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

        JLabel titulo = new JLabel("🏆 Tabla de posiciones — última carrera", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        root.add(titulo, BorderLayout.NORTH);

        root.add(new JScrollPane(crearTabla()), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        btnSeguir.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnRanking.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnTerminar.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnSeguir.setMargin(new Insets(12, 35, 12, 35));
        btnRanking.setMargin(new Insets(12, 35, 12, 35));
        btnTerminar.setMargin(new Insets(12, 35, 12, 35));
        panelBotones.add(btnSeguir);
        panelBotones.add(btnRanking);
        panelBotones.add(btnTerminar);
        root.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JTable crearTabla() {
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"Posición", "Caballo", "Tipo", "Puntos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        PosicionDTO[] posiciones = juego.getTablaPosiciones();
        for (int i = 0; i < posiciones.length; i++) {
            PosicionDTO p = posiciones[i];
            String nombre = p.getNombre() + (p.esDelJugador() ? " (vos)" : "");
            modelo.addRow(new Object[]{p.getPosicion() + "°", nombre, p.getTipo(), p.getPuntos()});
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(40);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        tabla.setEnabled(false);
        return tabla;
    }

    private void registrarEventos() {
        // seguir jugando: vuelve a la configuracion (mismo jugador, sigue sumando puntos)
        btnSeguir.addActionListener(e -> {
            new PantallaConfiguracion(juego).setVisible(true);
            dispose();
        });
        // ver ranking: abre el ranking historico de la base
        btnRanking.addActionListener(e -> {
            new PantallaRanking(juego).setVisible(true);
            dispose();
        });
        // terminar: cierra el programa
        btnTerminar.addActionListener(e -> System.exit(0));
    }
}
