INSERT INTO `notificaciones`
(`id_notificacion`, `id_usuario`, `id_reserva`, `id_cancelacion`, `mensaje`, `tipo`, `leida`,`fecha_envio`)
VALUES
    (1, 101, 5001, NULL, 'Reserva confirmada correctamente', 'RESERVA', 0,"2026-06-28"),
    (2, 102, NULL, 3001, 'Su reserva ha sido cancelada', 'CANCELACION', 1,"2026-06-29");