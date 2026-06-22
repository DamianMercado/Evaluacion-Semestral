CREATE TABLE `cancelaciones` (
                                 `id_cancelacion` BIGINT NOT NULL,
                                 `motivo` VARCHAR(255) NOT NULL,
                                 `fecha_cancelacion` DATE NOT NULL,
                                 `estado_reembolso` VARCHAR(50) NOT NULL,
                                 `id_reserva` BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `cancelaciones`
    ADD PRIMARY KEY (`id_cancelacion`);

ALTER TABLE `cancelaciones`
    MODIFY `id_cancelacion`
    BIGINT NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT=1;

COMMIT;