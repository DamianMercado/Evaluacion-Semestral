package com.ReservaPro.ms_pago.enums;

public enum Metodo {
    CREDITO("CREDITO"),
    DEBITO("DEBITO"),
    EFECTIVO("EFECTIVO");

    private final String valor;

    Metodo(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static Metodo fromValor(String valor) {
        for (Metodo m : values()) {
            if (m.valor.equalsIgnoreCase(valor)) {
                return m;
            }
        }
        throw new IllegalArgumentException("Método de pago desconocido: " + valor);
    }
}