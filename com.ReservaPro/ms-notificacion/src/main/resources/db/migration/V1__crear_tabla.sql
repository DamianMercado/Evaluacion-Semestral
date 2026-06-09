CREATE TABLE `notificaciones` (
                                  `id_notificacion` BIGINT(20) NOT NULL,
                                  `id_usuario` BIGINT(20) NOT NULL,
                                  `id_reserva` BIGINT(20) DEFAULT NULL,
                                  `id_cancelacion` BIGINT(20) DEFAULT NULL,
                                  `mensaje` VARCHAR(200) NOT NULL,
                                  `tipo` VARCHAR(20) NOT NULL,
                                  `leida` TINYINT(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Índices
--

ALTER TABLE `notificaciones`
    ADD PRIMARY KEY (`id_notificacion`);

--
-- AUTO_INCREMENT
--

ALTER TABLE `notificaciones`
    MODIFY `id_notificacion` BIGINT(20) NOT NULL AUTO_INCREMENT;

COMMIT;