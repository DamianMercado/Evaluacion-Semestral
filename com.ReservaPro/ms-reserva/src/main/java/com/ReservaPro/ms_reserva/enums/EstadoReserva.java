package com.ReservaPro.ms_reserva.enums;

public enum EstadoReserva {

    PENDIENTE_PAGO("PENDIENTE_PAGO"),
    PAGADO("PAGADO"),
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

    public boolean puedeTransicionarA(EstadoReserva nuevoEstado) {
        return switch (this) {
            case PENDIENTE_PAGO -> nuevoEstado == PAGADO || nuevoEstado == CANCELADA;
            case PAGADO -> nuevoEstado == CONFIRMADA || nuevoEstado == CANCELADA;
            case CONFIRMADA -> nuevoEstado == COMPLETADA || nuevoEstado == CANCELADA;
            case COMPLETADA -> false; // Estado final
            case CANCELADA -> false; // Estado final
        };
    }
}