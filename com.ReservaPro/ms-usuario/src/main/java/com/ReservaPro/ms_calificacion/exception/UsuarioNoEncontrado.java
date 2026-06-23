package com.ReservaPro.ms_calificacion.exception;

public class UsuarioNoEncontrado extends RuntimeException {

    public UsuarioNoEncontrado(Long id) {
        super("No se ha encontrado el usuario con el id: " + id);
    }
}