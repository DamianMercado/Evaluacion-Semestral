CREATE TABLE IF NOT EXISTS reserva (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_gestion_servicio BIGINT NOT NULL,
    id_promocion BIGINT,
    id_calificacion BIGINT,
    id_pago BIGINT,
    fecha_reserva TIMESTAMP NOT NULL,
    precio_reserva DECIMAL(10,2) NOT NULL,
    descuento_aplicado DECIMAL(10,2) DEFAULT 0,
    precio_final DECIMAL(10,2) NOT NULL,
    estado_reserva VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE_PAGO',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE INDEX idx_reserva_usuario ON reserva(id_usuario);
CREATE INDEX idx_reserva_estado ON reserva(estado_reserva);
CREATE INDEX idx_reserva_pago ON reserva(id_pago);