package com.uade.carreras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "posicion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"carrera_id", "caballo_id"}))
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @ManyToOne(optional = false)
    @JoinColumn(name = "caballo_id")
    private Caballo caballo;

    @Column(nullable = false)
    private int posicion;

    @Column(nullable = false)
    private int puntos;

    @Column(name = "es_del_jugador", nullable = false)
    private boolean esDelJugador;

    protected Posicion() {
       
    }

    public Posicion(Carrera carrera, Caballo caballo, int posicion,
                    int puntos, boolean esDelJugador) {
        this.carrera = carrera;
        this.caballo = caballo;
        this.posicion = posicion;
        this.puntos = puntos;
        this.esDelJugador = esDelJugador;
    }

    public int getId() {
        return id;
    }
}
