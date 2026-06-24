package com.uade.carreras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pista")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private int distancia;

    protected Pista() {
        // Constructor requerido por JPA/Hibernate
    }

    public Pista(String nombre, int distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }

    public Pista(int id, String nombre, int distancia) {
        this.id = id;
        this.nombre = nombre;
        this.distancia = distancia;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getDistancia() { return distancia; }

    @Override
    public String toString() {
        return nombre + "   (" + distancia + " metros)";
    }
}
