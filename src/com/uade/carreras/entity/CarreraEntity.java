package com.uade.carreras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "carrera")
public class CarreraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jugador_id")
    private JugadorEntity jugador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pista_id")
    private PistaEntity pista;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caballo_jugador_id")
    private CaballoEntity caballoElegido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caballo_ganador_id")
    private CaballoEntity caballoGanador;

    @Column(name = "gano_el_jugador", nullable = false)
    private boolean ganoElJugador;

    public CarreraEntity() {
    }

    public CarreraEntity(JugadorEntity jugador, PistaEntity pista, CaballoEntity caballoElegido,
                         CaballoEntity caballoGanador, boolean ganoElJugador) {
        this.fecha = LocalDateTime.now();
        this.jugador = jugador;
        this.pista = pista;
        this.caballoElegido = caballoElegido;
        this.caballoGanador = caballoGanador;
        this.ganoElJugador = ganoElJugador;
    }

    public int getId() {
        return id;
    }
}
