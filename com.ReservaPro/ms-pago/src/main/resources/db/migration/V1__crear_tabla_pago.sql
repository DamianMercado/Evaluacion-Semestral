CREATE TABLE IF NOT EXISTS pago (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT NOT NULL,
    monto_original DOUBLE(10,2) NOT NULL,
    monto DOUBLE(10,2) NOT NULL,
    metodo VARCHAR(100) NOT NULL,
    banco VARCHAR(100) NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    fecha DATE NOT NULL,
    aplica_descuento BOOLEAN NOT NULL DEFAULT FALSE,
    codigo_promocion VARCHAR(50),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE INDEX idx_pago_reserva ON pago(id_reserva);
CREATE INDEX idx_pago_estado ON pago(estado);