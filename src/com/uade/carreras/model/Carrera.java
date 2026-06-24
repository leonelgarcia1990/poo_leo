package com.uade.carreras.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrera")
public class Carrera {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pista_id")
    private Pista pista;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caballo_jugador_id")
    private Caballo caballoElegido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caballo_ganador_id")
    private Caballo caballoGanador;

    @Column(name = "gano_el_jugador", nullable = false)
    private boolean jugadorGano;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posicion> posiciones = new ArrayList<>();

  

    @Transient
    private double distanciaTotal;
    @Transient
    private Caballo[] caballos;
    @Transient
    private Caballo caballoJugador;
    @Transient
    private boolean carreraFinalizada;
    @Transient
    private Caballo ganador;

    protected Carrera() {
     
    }

    public Carrera(double distanciaTotal, Caballo[] caballos, Caballo caballoJugador) {
        this.distanciaTotal = distanciaTotal;
        this.caballos = caballos;
        this.caballoJugador = caballoJugador;
        this.carreraFinalizada = false;
        this.ganador = null;
    }


    public void confirmar(Jugador jugador, Pista pista, Caballo caballoElegido,
                          Caballo caballoGanador, boolean jugadorGano) {
        this.fecha = LocalDateTime.now();
        this.jugador = jugador;
        this.pista = pista;
        this.caballoElegido = caballoElegido;
        this.caballoGanador = caballoGanador;
        this.jugadorGano = jugadorGano;
    }

    public void agregarPosicion(Posicion posicion) {
        this.posiciones.add(posicion);
    }

    public int getId() { return id; }

    

    public double getDistanciaTotal() { return distanciaTotal; }
    public Caballo[] getCaballos() { return caballos; }
    public Caballo getCaballoJugador() { return caballoJugador; }
    public boolean isCarreraFinalizada() { return carreraFinalizada; }
    public Caballo getGanador() { return ganador; }

    public void paso() {
        if (carreraFinalizada) {
            return;
        }
        for (int i = 0; i < caballos.length; i++) {
            caballos[i].avanzar();
        }

        if (ganador == null) {
            this.ganador = obtenerGanador();
        }

        if (todosLlegaron()) {
            this.carreraFinalizada = true;
        }
    }

    private boolean todosLlegaron() {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getDistanciaRecorrida() < distanciaTotal) {
                return false;
            }
        }
        return true;
    }

    public boolean esDelJugador(Caballo caballo) {
        return caballo == caballoJugador;
    }

    public boolean ganoElJugador() {
        return ganador == caballoJugador;
    }

    private Caballo obtenerGanador() {
        Caballo mejor = null;
        for (int i = 0; i < caballos.length; i++) {
            Caballo c = caballos[i];
            if (c.getDistanciaRecorrida() >= distanciaTotal) {
                if (mejor == null || c.getDistanciaRecorrida() > mejor.getDistanciaRecorrida()) {
                    mejor = c;
                }
            }
        }
        return mejor;
    }

    public int calcularPuntos(int posicion) {
        if (posicion == 1) return 100;
        if (posicion == 2) return 50;
        return 10;
    }

    public int obtenerPosicion(Caballo objetivo) {
        int posicion = 1;
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i].getDistanciaRecorrida() > objetivo.getDistanciaRecorrida()) {
                posicion++;
            }
        }
        return posicion;
    }

    public Caballo[] getCaballosOrdenados() {

        Caballo[] orden = new Caballo[caballos.length];
        for (int i = 0; i < caballos.length; i++) {
            orden[i] = caballos[i];
        }
        for (int i = 0; i < orden.length; i++) {
            for (int j = i + 1; j < orden.length; j++) {
                if (orden[j].getDistanciaRecorrida() > orden[i].getDistanciaRecorrida()) {
                    Caballo tmp = orden[i];
                    orden[i] = orden[j];
                    orden[j] = tmp;
                }
            }
        }
        return orden;
    }
}
