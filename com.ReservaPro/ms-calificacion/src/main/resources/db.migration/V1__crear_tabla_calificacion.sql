CREATE TABLE IF NOT EXISTS calificacion (
                                            id_calificacion BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            id_reserva BIGINT NOT NULL,
                                            id_usuario BIGINT NOT NULL,
                                            puntuacion INT NOT NULL CHECK (puntuacion BETWEEN 1 AND 5),
    comentario VARCHAR(500),
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE INDEX idx_calificacion_usuario ON calificacion(id_usuario);
CREATE INDEX idx_calificacion_reserva ON calificacion(id_reserva);
CREATE INDEX idx_calificacion_estado ON calificacion(estado);