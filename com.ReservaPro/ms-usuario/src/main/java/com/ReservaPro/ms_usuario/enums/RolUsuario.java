package com.ReservaPro.ms_usuario.enums;

public enum RolUsuario {
    Adminstrador ("Adminstrador"),
    Cliente ("Cliente"),
    OperadorServicio ("OperadorServicio");


    private final String valor;

    RolUsuario(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static RolUsuario fromValor(String valor) {


        // Recorre todos los valores definidos en el enum
        for (RolUsuario r : values()) {


            // Compara el valor recibido con el valor del enum
            if (r.valor.equals(valor)) return r;
        }


        // Si no encuentra coincidencia, lanza excepción controlada
        throw new IllegalArgumentException("Rol de usuario desconocido: " + valor);
    }

}
