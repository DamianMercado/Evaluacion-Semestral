package com.ReservaPro.ms_usuario.exception;

public class UsuarioNoEncontrado extends RuntimeException {

    public UsuarioNoEncontrado(String id) {
        super("No se encontró ningún usuario con el id " + id);
    }
}
