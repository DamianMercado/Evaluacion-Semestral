package com.ReservaPro.ms_calificacion.enums;

public enum RolUsuario {

    ADMINISTRADOR("ADMINISTRADOR"),
    CLIENTE("CLIENTE"),
    OPERADOR_SERVICIO("OPERADOR_SERVICIO");

    private final String valor;

    RolUsuario(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static RolUsuario fromValor(String valor) {

        for (RolUsuario r : values()) {

            if (r.valor.equalsIgnoreCase(valor)) {
                return r;
            }
        }

        throw new IllegalArgumentException(
                "Rol de usuario desconocido: " + valor
        );
    }
}