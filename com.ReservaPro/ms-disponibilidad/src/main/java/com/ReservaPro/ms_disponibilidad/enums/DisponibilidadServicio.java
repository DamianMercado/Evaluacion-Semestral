package com.ReservaPro.ms_disponibilidad.enums;

public enum DisponibilidadServicio {
    Reservado("Reservado"),
    Disponible("Disponible")
    ;


    // Atributo que almacena el valor asociado al estado (ej: "Creado", "Reservado", etc.)
    private final String valor;


    // Constructor del enum que permite asignar un valor a cada constante
    EstadoEvento(String valor) {
        this.valor = valor;
    }


    // Método getter para obtener el valor del estado en formato String
    public String getValor() {
        return valor;
    }


    // Método estático que permite convertir un String en su correspondiente EstadoEvento
    public static DisponibilidadServicio fromValor(String valor) {


        // Recorre todos los valores definidos en el enum
        for (DisponibilidadServicio e : values()) {


            // Compara el valor recibido con el valor del enum
            if (e.valor.equals(valor)) return e;
        }


        // Si no encuentra coincidencia, lanza una excepción indicando error
        throw new IllegalArgumentException("Estado de evento desconocido: " + valor);
    }

}
