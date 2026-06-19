CREATE TABLE usuario (

                         id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,

                         nombre VARCHAR(100) NOT NULL,

                         apellido VARCHAR(100) NOT NULL,

                         password VARCHAR(100) NOT NULL,

                         email VARCHAR(100) NOT NULL UNIQUE,

                         rut VARCHAR(12) NOT NULL UNIQUE,

                         rol VARCHAR(30) NOT NULL
);