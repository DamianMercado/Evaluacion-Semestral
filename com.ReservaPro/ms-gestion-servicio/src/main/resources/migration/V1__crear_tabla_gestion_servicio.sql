CREATE TABLE IF NOT EXISTS gestion_servicio (
                                                id_gestion_servicio BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                nombre VARCHAR(100) NOT NULL,
    precio_servicio DECIMAL(10,2) NOT NULL,
    duracion_minuto INT NOT NULL,
    estado_servicio VARCHAR(30) NOT NULL DEFAULT 'ACTIVADO',
    ubicacion VARCHAR(200) NOT NULL,
    capacidad INT NOT NULL,
    condiciones VARCHAR(500) NOT NULL,
    proveedor_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE INDEX idx_gestion_servicio_estado ON gestion_servicio(estado_servicio);
CREATE INDEX idx_gestion_servicio_nombre ON gestion_servicio(nombre);
CREATE INDEX idx_gestion_servicio_proveedor ON gestion_servicio(proveedor_id);