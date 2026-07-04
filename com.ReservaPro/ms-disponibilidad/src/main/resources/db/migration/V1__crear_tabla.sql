CREATE TABLE `disponibilidades` (
                                    `id_disponibilidad` BIGINT(20) NOT NULL,
                                    `fecha` DATE NOT NULL,
                                    `hora_inicio` TIME NOT NULL,
                                    `hora_fin` TIME NOT NULL,
                                    `cupos_disponibles` INT NOT NULL,
                                    `cupos_totales` INT NOT NULL,
                                    `estado` VARCHAR(20) NOT NULL,
                                    `fecha_creacion` DATETIME NOT NULL,
                                    `fecha_actualizacion` DATETIME NOT NULL,
                                    `observacion` VARCHAR(200) DEFAULT NULL,
                                    `activo` TINYINT(1) NOT NULL,
                                    `id_servicio` BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `disponibilidades`
    ADD PRIMARY KEY (`id_disponibilidad`);

ALTER TABLE `disponibilidades`
    MODIFY `id_disponibilidad` BIGINT(20) NOT NULL AUTO_INCREMENT;

COMMIT;