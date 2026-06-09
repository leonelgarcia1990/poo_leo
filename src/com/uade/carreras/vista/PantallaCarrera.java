package com.uade.carreras.vista;

import com.uade.carreras.controlador.ControladorJuego;
import com.uade.carreras.controlador.ControladorCarrera;
import com.uade.carreras.dto.CarrilDTO;
import com.uade.carreras.dto.ResultadoCarreraDTO;

import javax.swing.*;
import java.awt.*;

public class PantallaCarrera extends JFrame {

    private int msPorPaso = 300;
    private int casillas = 48; // largo de la pista en "casilleros"

    private ControladorJuego juego;
    private ControladorCarrera controlador;
    private CarrilDTO[] carriles;
    private JLabel[] pistas;

    private JLabel estado = new JLabel("¡Largaron! 🏇", SwingConstants.CENTER);
    private JButton btnPosiciones = new JButton("Ver tabla de posiciones 🏁");
    private Timer timer;

    private Font fuenteTexto = new Font("SansSerif", Font.PLAIN, 18);
    private Font fuentePista = new Font("Monospaced", Font.PLAIN, 28);

    public PantallaCarrera(ControladorJuego juego, ControladorCarrera controlador) {
        this.juego = juego;
        this.controlador = controlador;
        this.carriles = controlador.getCarriles();
        this.pistas = new JLabel[carriles.length];

        setTitle("Carrera en curso");
        // tamaño segun el monitor (75% del ancho y alto de la pantalla)
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (pantalla.width * 0.75), (int) (pantalla.height * 0.75));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        construirPantalla();
        registrarEventos();
        iniciarTimer();
    }

    private void construirPantalla() {
        JPanel root = new JPanel(new BorderLayout(10, 20));
        root.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // arriba: nombre de la pista y la distancia
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1, 0, 5));
        JLabel titulo = new JLabel("🏁 " + controlador.getNombrePista(), SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        JLabel subtitulo = new JLabel("Distancia: " + controlador.getDistanciaTotal() + " metros", SwingConstants.CENTER);
        subtitulo.setFont(fuenteTexto);
        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);
        root.add(panelTitulo, BorderLayout.NORTH);

        // en el medio: un carril por caballo (nombre + la pista con el caballito)
        JPanel panelCarriles = new JPanel(new GridLayout(carriles.length, 1, 0, 20));
        for (int i = 0; i < carriles.length; i++) {
            panelCarriles.add(crearCarril(i));
        }
        root.add(panelCarriles, BorderLayout.CENTER);

        // abajo: mensaje de estado y el boton (oculto hasta que termine)
        JPanel panelSur = new JPanel(new GridLayout(2, 1, 0, 10));
        estado.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnPosiciones.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnPosiciones.setMargin(new Insets(12, 35, 12, 35));
        btnPosiciones.setVisible(false);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnPosiciones);
        panelSur.add(estado);
        panelSur.add(panelBoton);
        root.add(panelSur, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel crearCarril(int i) {
        CarrilDTO carril = carriles[i];
        JPanel panelCarril = new JPanel(new BorderLayout(15, 0));

        String etiqueta = carril.getNombre() + " [" + carril.getTipo() + "]" + (carril.esDelJugador() ? " (vos)" : "");
        JLabel lblNombre = new JLabel(etiqueta);
        lblNombre.setFont(fuenteTexto);
        lblNombre.setPreferredSize(new Dimension(260, 30));
        panelCarril.add(lblNombre, BorderLayout.WEST);

        // la pista es una linea con el caballo arrancando en el principio
        JLabel pista = new JLabel(dibujarPista(0));
        pista.setFont(fuentePista);
        if (carril.esDelJugador()) {
            pista.setForeground(new Color(40, 130, 70));
        }
        panelCarril.add(pista, BorderLayout.CENTER);

        pistas[i] = pista;
        return panelCarril;
    }

    // arma la linea de la pista: guiones y el caballo en la posicion que avanzo
    private String dibujarPista(int recorrido) {
        int total = controlador.getDistanciaTotal();
        int pos = (int) ((double) recorrido / total * casillas);
        if (pos >= casillas) {
            pos = casillas - 1;
        }
        String linea = "";
        for (int k = 0; k < casillas; k++) {
            if (k == pos) {
                linea += "🐎";
            } else {
                linea += "─";
            }
        }
        linea += "🏁";
        return linea;
    }

    private void registrarEventos() {
        btnPosiciones.addActionListener(e -> {
            new PantallaPosiciones(juego).setVisible(true);
            dispose();
        });
    }

    // el timer hace avanzar la carrera de a poco y va moviendo los caballos
    private void iniciarTimer() {
        timer = new Timer(msPorPaso, e -> {
            controlador.avanzarUnPaso();
            actualizarCarriles();
            if (controlador.termino()) {
                timer.stop();
                mostrarGanador();
            }
        });
        timer.start();
    }

    private void actualizarCarriles() {
        CarrilDTO[] actuales = controlador.getCarriles();
        for (int i = 0; i < actuales.length; i++) {
            int recorrido = actuales[i].getDistanciaRecorrida();
            pistas[i].setText(dibujarPista(recorrido));
        }
    }

    private void mostrarGanador() {
        ResultadoCarreraDTO resultado = controlador.getResultado();
        String nombreGanador = resultado.getNombreGanador();
        String nombreJugador = resultado.getNombreJugador();
        boolean ganoElJugador = resultado.ganoElJugador();

        estado.setText("🏆 Ganó: " + nombreGanador + (ganoElJugador ? " — ¡Ganaste! 🎉" : ""));
        btnPosiciones.setVisible(true);

        // armo el cartel con varios labels para que se vea mas grande
        Font fuente = new Font("SansSerif", Font.PLAIN, 22);
        JPanel cartel = new JPanel(new GridLayout(0, 1, 0, 8));
        cartel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblGanador = new JLabel("🏆 Ganador: " + nombreGanador + " [" + resultado.getTipoGanador() + "]");
        lblGanador.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel lblJugador = new JLabel("Jugador: " + nombreJugador);
        JLabel lblPosicion = new JLabel("Tu posición: " + resultado.getPosicionJugador() + "°");
        JLabel lblGanados = new JLabel("Puntos ganados: " + resultado.getPuntajeGanado());
        JLabel lblTotal = new JLabel("Puntaje total: " + resultado.getPuntajeTotal());
        lblJugador.setFont(fuente);
        lblPosicion.setFont(fuente);
        lblGanados.setFont(fuente);
        lblTotal.setFont(fuente);

        cartel.add(lblGanador);
        cartel.add(lblJugador);
        cartel.add(lblPosicion);
        cartel.add(lblGanados);
        cartel.add(lblTotal);

        JOptionPane.showMessageDialog(this, cartel, "Fin de la carrera", JOptionPane.PLAIN_MESSAGE);
    }
}
