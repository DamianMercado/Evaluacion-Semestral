CREATE TABLE `promocion` (
                             `id_promocion` bigint(20) NOT NULL,
                             `activa_promocion` bit(1) DEFAULT NULL,
                             `codigo` varchar(255) NOT NULL,
                             `descripcion` varchar(255) NOT NULL,
                             `porcentaje_descuento` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `promocion`
--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
    ADD PRIMARY KEY (`id_promocion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `promocion`
--
ALTER TABLE `promocion`
    MODIFY `id_promocion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;