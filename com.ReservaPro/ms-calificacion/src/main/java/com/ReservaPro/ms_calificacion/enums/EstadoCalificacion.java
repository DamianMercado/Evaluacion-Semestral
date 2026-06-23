package com.ReservaPro.ms_calificacion.enums;

public enum EstadoCalificacion {
    PENDIENTE("PENDIENTE"),
    PUBLICADA("PUBLICADA"),
    MODERADA("MODERADA"),
    ELIMINADA("ELIMINADA");

    private final String valor;

    EstadoCalificacion(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoCalificacion fromValor(String valor) {

        for (EstadoCalificacion r : values()) {

            if (r.valor.equalsIgnoreCase(valor)) {
                return r;
            }
        }

        throw new IllegalArgumentException(
                "Estado de calificacion desconocida: " + valor
        );
    }
}