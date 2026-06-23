INSERT INTO calificacion (id_reserva, id_usuario, puntuacion, comentario, estado, fecha_creacion)
VALUES
    (1, 1, 5, 'Excelente servicio, muy recomendado', 'PUBLICADA', NOW()),
    (2, 2, 4, 'Buen servicio, pero podría mejorar', 'PUBLICADA', NOW()),
    (3, 3, 3, 'Servicio regular', 'PENDIENTE', NOW());