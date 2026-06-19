INSERT INTO historial_reserva (
    id_reserva,
    estado_anterior,
    estado_nuevo,
    fecha_cambio,
    observacion
)
VALUES
    (1, 'PENDIENTE', 'CONFIRMADA', NOW(), 'Reserva confirmada'),
    (2, 'CONFIRMADA', 'CANCELADA', NOW(), 'Cancelación solicitada'),
    (3, 'PENDIENTE', 'COMPLETADA', NOW(), 'Servicio finalizado');