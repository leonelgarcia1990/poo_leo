-- ============================================================
--  Carreras de Caballos - Esquema de base de datos (MySQL)
--  Guarda jugadores, caballos, pistas, carreras y la tabla
--  de posiciones (con puntajes) de cada carrera.
-- ============================================================

-- PASO 1: crear la base y posicionarse en ella ---------------
CREATE DATABASE IF NOT EXISTS carreras
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE carreras;

-- PASO 2: JUGADOR -------------------------------------------
-- El email es la identidad del jugador (igual que en GestorJugadores).
CREATE TABLE IF NOT EXISTS jugador (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(100) NOT NULL,
    email             VARCHAR(150) NOT NULL,
    puntaje_acumulado INT NOT NULL DEFAULT 0,
    CONSTRAINT uq_jugador_email UNIQUE (email)
);

-- PASO 3: CABALLO (catálogo) --------------------------------
-- tipo: VELOCISTA / RESISTENTE / EQUILIBRADO (lo que devuelve getTipo()).
CREATE TABLE IF NOT EXISTS caballo (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    tipo           VARCHAR(20)  NOT NULL,
    velocidad_base INT NOT NULL,
    resistencia    INT NOT NULL
);

-- PASO 4: PISTA ---------------------------------------------
CREATE TABLE IF NOT EXISTS pista (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    distancia INT NOT NULL          -- en metros
);

-- PASO 5: CARRERA (una corrida concreta) --------------------
-- Relaciona jugador + pista + el caballo que eligió + el ganador.
CREATE TABLE IF NOT EXISTS carrera (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    fecha               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    jugador_id          INT NOT NULL,
    pista_id            INT NOT NULL,
    caballo_jugador_id  INT NOT NULL,   -- el caballo que apostó el jugador
    caballo_ganador_id  INT NOT NULL,   -- el que cruzó primero
    gano_el_jugador     BOOLEAN NOT NULL,
    CONSTRAINT fk_carrera_jugador  FOREIGN KEY (jugador_id)         REFERENCES jugador(id),
    CONSTRAINT fk_carrera_pista    FOREIGN KEY (pista_id)           REFERENCES pista(id),
    CONSTRAINT fk_carrera_cjugador FOREIGN KEY (caballo_jugador_id) REFERENCES caballo(id),
    CONSTRAINT fk_carrera_ganador  FOREIGN KEY (caballo_ganador_id) REFERENCES caballo(id)
);

-- PASO 6: POSICION (la tabla de posiciones de cada carrera) -
-- Una fila por caballo en cada carrera = exactamente tu PosicionDTO.
CREATE TABLE IF NOT EXISTS posicion (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    carrera_id    INT NOT NULL,
    caballo_id    INT NOT NULL,
    posicion      INT NOT NULL,        -- 1 = primero
    puntos        INT NOT NULL,        -- según GestorPuntaje (100/60/30/10)
    es_del_jugador BOOLEAN NOT NULL,
    CONSTRAINT fk_posicion_carrera FOREIGN KEY (carrera_id) REFERENCES carrera(id) ON DELETE CASCADE,
    CONSTRAINT fk_posicion_caballo FOREIGN KEY (caballo_id) REFERENCES caballo(id),
    CONSTRAINT uq_posicion UNIQUE (carrera_id, caballo_id)   -- un caballo no se repite en una carrera
);

-- PASO 7: datos iniciales (catálogo de caballos y pistas) ----
-- Mismos valores que GestorCaballos.
INSERT INTO caballo (nombre, tipo, velocidad_base, resistencia) VALUES
    ('Relámpago', 'VELOCISTA',   92, 55),
    ('Tormenta',  'RESISTENTE',  68, 92),
    ('Furia',     'VELOCISTA',   90, 50),
    ('Centella',  'EQUILIBRADO', 80, 80);

INSERT INTO pista (nombre, distancia) VALUES
    ('Gran Premio Nacional',          2500),
    ('Gran Premio Carlos Pellegrini', 2400),
    ('Gran Premio Jockey Club',       2000);
