package com.uade.carreras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "jugador", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(name = "puntaje_acumulado", nullable = false)
    private int puntajeAcumulado;

    protected Jugador() {
        // Constructor requerido por JPA/Hibernate
    }

    public Jugador(String nombre, String email) {
        this(nombre, email, 0);
    }

    public Jugador(String nombre, String email, int puntajeInicial) {
        this.nombre = nombre;
        this.email = email;
        this.puntajeAcumulado = puntajeInicial;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getPuntajeAcumulado() { return puntajeAcumulado; }

    public void agregarPuntaje(int puntos) {
        this.puntajeAcumulado += puntos;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }
}
