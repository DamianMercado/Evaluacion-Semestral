CREATE TABLE historial_reserva (
                                   id_historial BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   id_reserva BIGINT NOT NULL,
                                   estado_anterior VARCHAR(50) NOT NULL,
                                   estado_nuevo VARCHAR(50) NOT NULL,
                                   fecha_cambio DATETIME NOT NULL,
                                   observacion VARCHAR(255)
);