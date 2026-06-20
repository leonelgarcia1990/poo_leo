package com.uade.carreras.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "caballo")
public class CaballoEntity {

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

    public CaballoEntity() {
    }

    public CaballoEntity(String nombre, String tipo, int velocidadBase, int resistencia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.velocidadBase = velocidadBase;
        this.resistencia = resistencia;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getVelocidadBase() {
        return velocidadBase;
    }

    public int getResistencia() {
        return resistencia;
    }
}
