package com.uade.carreras.vista;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.controlador.ControladorCarrera;
import com.uade.carreras.dto.CaballoDTO;
import com.uade.carreras.dto.PistaDTO;
import com.uade.carreras.dto.ConfiguracionCarreraDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PantallaConfiguracion extends JFrame {

    private JTextField nombreField = new JTextField();
    private JTextField emailField = new JTextField();

    private ControladorJuego controladorJuego;

    private CaballoDTO[] caballos;
    private PistaDTO[] pistas;

    private JRadioButton[] caballoRadios;
    private JRadioButton[] pistaRadios;

    private ButtonGroup grupoCaballos = new ButtonGroup();
    private ButtonGroup grupoPistas = new ButtonGroup();

    private JButton btnIniciar = new JButton("Iniciar Carrera");

    private Font fuenteTexto = new Font("SansSerif", Font.PLAIN, 18);
    private Font fuenteTitulo = new Font("SansSerif", Font.BOLD, 18);

    public PantallaConfiguracion(ControladorJuego juego) {
        this.controladorJuego = juego;
        this.caballos = juego.getCaballosDisponibles();
        this.pistas = juego.getPistasDisponibles();
        this.caballoRadios = new JRadioButton[caballos.length];
        this.pistaRadios = new JRadioButton[pistas.length];

        setTitle("Configuración de la Carrera");
        setSize(1100, 680);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        construirPantalla();
        registrarEventos();

        if (juego.hayJugador()) {
            nombreField.setText(juego.getNombreJugador());
            emailField.setText(juego.getEmailJugador());
            nombreField.setEnabled(false);
            emailField.setEnabled(false);
        }
    }

    private void construirPantalla() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Configurá tu Carrera", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
        root.add(titulo, BorderLayout.NORTH);

        JPanel caballosYPistas = new JPanel(new BorderLayout(0, 12));
        caballosYPistas.add(crearSeccionCaballos(), BorderLayout.NORTH);
        caballosYPistas.add(crearSeccionPistas(), BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 12));
        contenido.add(crearSeccionJugador(), BorderLayout.NORTH);
        contenido.add(caballosYPistas, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
        wrapper.add(contenido);
        root.add(wrapper, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnIniciar.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnIniciar.setMargin(new Insets(15, 40, 15, 40));
        panelBoton.add(btnIniciar);
        root.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel crearSeccionJugador() {
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder borde = BorderFactory.createTitledBorder("1. Datos del jugador");
        borde.setTitleFont(fuenteTitulo);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde, BorderFactory.createEmptyBorder(6, 18, 6, 18)));

        JPanel form = new JPanel(new GridLayout(2, 1, 5, 5));

        nombreField.setPreferredSize(new Dimension(350, 32));
        emailField.setPreferredSize(new Dimension(350, 32));
        nombreField.setFont(fuenteTexto);
        emailField.setFont(fuenteTexto);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteTexto);
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(fuenteTexto);

        JPanel filaNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaNombre.add(lblNombre);
        filaNombre.add(nombreField);

        JPanel filaEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaEmail.add(lblEmail);
        filaEmail.add(emailField);

        form.add(filaNombre);
        form.add(filaEmail);

        panel.add(form, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearSeccionCaballos() {
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder borde = BorderFactory.createTitledBorder("2. Elegí tu caballo");
        borde.setTitleFont(fuenteTitulo);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde, BorderFactory.createEmptyBorder(6, 18, 6, 18)));

        JPanel filas = new JPanel(new GridLayout(caballos.length, 1, 0, 8));

        for (int i = 0; i < caballos.length; i++) {
            JRadioButton radio = new JRadioButton(caballos[i].toString());
            radio.setFont(fuenteTexto);
            grupoCaballos.add(radio);
            caballoRadios[i] = radio;
            filas.add(radio);
        }
        panel.add(filas, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearSeccionPistas() {
        JPanel panel = new JPanel(new BorderLayout());
        TitledBorder borde = BorderFactory.createTitledBorder("3. Elegí la pista");
        borde.setTitleFont(fuenteTitulo);
        panel.setBorder(BorderFactory.createCompoundBorder(
                borde, BorderFactory.createEmptyBorder(6, 18, 6, 18)));

        JPanel filas = new JPanel(new GridLayout(pistas.length, 1, 0, 8));

        for (int i = 0; i < pistas.length; i++) {
            JRadioButton radio = new JRadioButton(pistas[i].toString());
            radio.setFont(fuenteTexto);
            grupoPistas.add(radio);
            pistaRadios[i] = radio;
            filas.add(radio);
        }
        panel.add(filas, BorderLayout.NORTH);
        return panel;
    }

    private void registrarEventos() {
        btnIniciar.addActionListener(e -> iniciarCarrera());
    }

    private void iniciarCarrera() {

        String nombre = nombreField.getText().trim();
        String email = emailField.getText().trim();

        if (nombre.length() < 3) {
            JOptionPane.showMessageDialog(this, "Ingresá un nombre de al menos 3 letras.");
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Ingresá un email válido (con @).");
            return;
        }
        if (indiceCaballoElegido() == -1) {
            JOptionPane.showMessageDialog(this, "Elegí un caballo.");
            return;
        }
        if (indicePistaElegida() == -1) {
            JOptionPane.showMessageDialog(this, "Elegí una pista.");
            return;
        }

        ConfiguracionCarreraDTO config = new ConfiguracionCarreraDTO(
                nombre, email, indiceCaballoElegido(), indicePistaElegida());

        ControladorCarrera controlador = controladorJuego.iniciarCarrera(config);
        new PantallaCarrera(controladorJuego, controlador).setVisible(true);
        dispose();
    }

    private int indiceCaballoElegido() {
        for (int i = 0; i < caballoRadios.length; i++) {
            if (caballoRadios[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private int indicePistaElegida() {
        for (int i = 0; i < pistaRadios.length; i++) {
            if (pistaRadios[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }
}
