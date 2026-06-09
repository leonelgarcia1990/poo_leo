package com.uade.carreras.dto;

// lo que el jugador eligio en la pantalla de configuracion
public class ConfiguracionCarreraDTO {

    private String nombre;
    private String email;
    private int indiceCaballo;
    private int indicePista;

    public ConfiguracionCarreraDTO(String nombre, String email, int indiceCaballo, int indicePista) {
        this.nombre = nombre;
        this.email = email;
        this.indiceCaballo = indiceCaballo;
        this.indicePista = indicePista;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getIndiceCaballo() { return indiceCaballo; }
    public int getIndicePista() { return indicePista; }
}
