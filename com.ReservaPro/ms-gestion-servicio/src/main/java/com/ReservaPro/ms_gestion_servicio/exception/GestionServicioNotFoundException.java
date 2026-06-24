package com.ReservaPro.ms_gestion_servicio.exception;

public class GestionServicioNotFoundException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO = "Servicio no encontrado con ID: ";

    public GestionServicioNotFoundException(Long id) {
        super(MENSAJE_POR_DEFECTO + id);
    }

    public GestionServicioNotFoundException(String mensaje) {
        super(mensaje);
    }

    public GestionServicioNotFoundException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}