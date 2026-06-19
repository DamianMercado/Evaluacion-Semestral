package com.ReservaPro.ms_reserva.enums;

public enum EstadoReserva {

    PENDIENTE_PAGO("PENDIENTE_PAGO"),
    CONFIRMADA("CONFIRMADA"),
    CANCELADA("CANCELADA"),
    COMPLETADA("COMPLETADA");

    private final String valor;

    EstadoReserva(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoReserva fromValor(String valor) {

        for (EstadoReserva r : values()) {

            if (r.valor.equalsIgnoreCase(valor)) {
                return r;
            }
        }

        throw new IllegalArgumentException(
                "Estado de reserva desconocido: " + valor
        );
    }
}