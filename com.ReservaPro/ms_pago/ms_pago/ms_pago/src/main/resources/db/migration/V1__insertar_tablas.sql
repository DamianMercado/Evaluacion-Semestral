CREATE TABLE `pago` (
                        `id_pago` bigint(20) NOT NULL,
                        `estado` enum('FALLIDO','PAGADO','PENDIENTE','REEMBOLSO') NOT NULL,
                        `fecha` date NOT NULL,
                        `metodo` varchar(100) NOT NULL,
                        `monto` double NOT NULL,
                        `banco` enum('BANCO_ESTADO','BCI','CHILE','FALABELLA','SANTANDER','TENPO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
    ADD PRIMARY KEY (`id_pago`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
    MODIFY `id_pago` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;
COMMIT;
