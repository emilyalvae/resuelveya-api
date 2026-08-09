CREATE DATABASE IF NOT EXISTS resuelveya_db;
USE resuelveya_db;

-- 1. Tabla Especialidad
CREATE TABLE IF NOT EXISTS especialidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255)
);

-- 2. Tabla Padre Usuario
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
     rol VARCHAR(20)  NOT NULL
    
);

-- 3. Tabla Separada Cliente
CREATE TABLE IF NOT EXISTS cliente (
    usuario_id BIGINT PRIMARY KEY,
    direccion_hogar VARCHAR(200),
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- 4. Tabla Separada Tecnico
CREATE TABLE IF NOT EXISTS tecnico (
    usuario_id BIGINT PRIMARY KEY,
    anios_experiencia INT,
    calificacion_promedio DOUBLE,
    especialidad_id BIGINT,
    CONSTRAINT fk_tecnico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_tecnico_especialidad FOREIGN KEY (especialidad_id) REFERENCES especialidad(id)
);