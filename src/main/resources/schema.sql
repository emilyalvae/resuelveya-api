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
    foto_url VARCHAR(255),
    direccion VARCHAR(255),
    distrito VARCHAR(100),
    ciudad VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
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
    presentacion VARCHAR(1000),
    anios_experiencia INT DEFAULT 0,
    calificacion_promedio DOUBLE DEFAULT 0.0,
    yape_numero VARCHAR(20),
    plin_numero VARCHAR(20),
    titular_pago VARCHAR(120),
    especialidad_id BIGINT,
    CONSTRAINT fk_tecnico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_tecnico_especialidad FOREIGN KEY (especialidad_id) REFERENCES especialidad(id) ON DELETE SET NULL
);

-- 5. Tabla Servicio
CREATE TABLE IF NOT EXISTS servicio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    precio_estimado DECIMAL(10,2) NOT NULL,
    tiempo_estimado VARCHAR(50),
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    especialidad_id BIGINT NOT NULL,
    tecnico_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_servicio_especialidad FOREIGN KEY (especialidad_id) REFERENCES especialidad(id),
    CONSTRAINT fk_servicio_tecnico FOREIGN KEY (tecnico_id) REFERENCES tecnico(usuario_id) ON DELETE CASCADE
);

-- 6. Tabla Reseña
CREATE TABLE IF NOT EXISTS resenia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    calificacion INT NOT NULL,
    comentario VARCHAR(1000),
    cliente_id BIGINT NOT NULL,
    tecnico_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_resenia_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(usuario_id) ON DELETE CASCADE,
    CONSTRAINT fk_resenia_tecnico FOREIGN KEY (tecnico_id) REFERENCES tecnico(usuario_id) ON DELETE CASCADE
);