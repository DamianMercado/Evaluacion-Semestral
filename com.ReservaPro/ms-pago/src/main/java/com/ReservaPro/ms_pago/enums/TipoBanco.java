package com.ReservaPro.ms_pago.enums;

public enum TipoBanco {
    BANCO_ESTADO("BANCO_ESTADO"),
    FALABELLA("FALABELLA"),
    CHILE("CHILE"),
    SANTANDER("SANTANDER"),
    BCI("BCI"),
    TENPO("TENPO");

    private final String valor;

    TipoBanco(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoBanco fromValor(String valor) {
        for (TipoBanco t : values()) {
            if (t.valor.equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de banco desconocido: " + valor);
    }
}