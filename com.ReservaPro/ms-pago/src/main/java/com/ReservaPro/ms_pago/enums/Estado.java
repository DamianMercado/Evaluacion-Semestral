package com.ReservaPro.ms_pago.enums;

public enum Estado {
    PENDIENTE("PENDIENTE"),
    PAGADO("PAGADO"),
    FALLIDO("FALLIDO"),
    REEMBOLSO("REEMBOLSO");

    private final String valor;

    Estado(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static Estado fromValor(String valor) {
        for (Estado e : values()) {
            if (e.valor.equalsIgnoreCase(valor)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado de pago desconocido: " + valor);
    }
}