INSERT INTO `notificaciones`
(`id_notificacion`, `id_usuario`, `id_reserva`, `id_cancelacion`, `mensaje`, `tipo`, `leida`)
VALUES
    (1, 101, 5001, NULL, 'Reserva confirmada correctamente', 'RESERVA', 0),
    (2, 102, NULL, 3001, 'Su reserva ha sido cancelada', 'CANCELACION', 1);