package com.uade.carreras.model;

import com.uade.carreras.service.EstrategiaAvance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "caballo")
public class Caballo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(name = "velocidad_base", nullable = false)
    private int velocidadBase;

    @Column(nullable = false)
    private int resistencia;

    @Transient
    private int energiaActual = 100;
    @Transient
    private double distanciaRecorrida = 0;

    protected Caballo() {
        // Constructor requerido por JPA/Hibernate
    }

    public Caballo(String nombre, String tipo, int velocidadBase, int resistencia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
    }

    public void avanzar() {
        EstrategiaAvance.para(tipo).avanzar(this);
    }

    // Métodos que usa la estrategia para modificar el estado de la carrera
    public void avanzarDistancia(double distancia) {
        this.distanciaRecorrida += distancia;
    }

    public void reducirEnergia(int cantidad) {
        this.energiaActual -= cantidad;
        if (this.energiaActual < 0) {
            this.energiaActual = 0;
        }
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getVelocidadBase() { return velocidadBase; }
    public int getResistencia() { return resistencia; }
    public int getEnergiaActual() { return energiaActual; }
    public double getDistanciaRecorrida() { return distanciaRecorrida; }

    @Override
    public String toString() {
        return nombre + " [" + tipo + "]"
                + "   (Velocidad: " + velocidadBase
                + " | Resistencia: " + resistencia
                + " | Energía: " + energiaActual + ")";
    }
}
