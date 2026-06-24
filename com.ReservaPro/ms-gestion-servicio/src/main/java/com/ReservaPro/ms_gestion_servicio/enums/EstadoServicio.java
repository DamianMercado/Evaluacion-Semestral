package com.ReservaPro.ms_gestion_servicio.enums;

public enum EstadoServicio {
    ACTIVADO("ACTIVADO"),
    DESACTIVADO("DESACTIVADO"),
    MANTENIMIENTO("MANTENIMIENTO"),
    ELIMINADO("ELIMINADO");

    private final String valor;

    EstadoServicio(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoServicio fromValor(String valor) {

        for (EstadoServicio r : values()) {

            if (r.valor.equalsIgnoreCase(valor)) {
                return r;
            }
        }

        throw new IllegalArgumentException(
                "Estado del servicio es desconocido: " + valor
        );
    }

}
