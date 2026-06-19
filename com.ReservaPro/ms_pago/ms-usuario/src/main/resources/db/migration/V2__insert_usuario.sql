INSERT INTO usuario (
    nombre,
    apellido,
    password,
    email,
    rut,
    rol
)
VALUES
    (
        'Administrador',
        'Sistema',
        'admin123',
        'admin@reservapro.cl',
        '11111111-1',
        'ADMINISTRADOR'
    ),

    (
        'Juan',
        'Pérez',
        'cliente123',
        'juan@gmail.com',
        '22222222-2',
        'CLIENTE'
    ),

    (
        'Pedro',
        'González',
        'operador123',
        'pedro@gmail.com',
        '33333333-3',
        'OPERADOR_SERVICIO'
    );