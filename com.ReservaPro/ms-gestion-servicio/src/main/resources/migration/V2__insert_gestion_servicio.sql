INSERT INTO gestion_servicio (
    nombre,
    precio_servicio,
    duracion_minuto,
    estado_servicio,
    ubicacion,
    capacidad,
    condiciones,
    proveedor_id
)
VALUES
    (
        'Arriendo Cancha Fútbol',
        49.99,
        60,
        'ACTIVADO',
        'Sucursal Centro',
        22,
        'Se requiere pago anticipado y reserva con 24h de anticipación',
        1
    ),
    (
        'Arriendo Cancha Tenis',
        29.99,
        90,
        'ACTIVADO',
        'Sucursal Norte',
        4,
        'Incluye raquetas y pelotas',
        2
    ),
    (
        'Arriendo Sala Eventos',
        99.99,
        120,
        'DESACTIVADO',
        'Sucursal Sur',
        50,
        'Capacidad para 50 personas, incluye sonido',
        1
    ),
    (
        'Arriendo Cancha Padel',
        39.99,
        60,
        'MANTENIMIENTO',
        'Sucursal Centro',
        8,
        'En mantenimiento por 2 semanas',
        3
    );