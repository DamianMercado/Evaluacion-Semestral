package com.ReservaPro.ms_reserva.dto.request;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaRequest {

    //Campos de otros ms
    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El idGestionServicio es obligatorio")
    private Long idGestionServicio;

    @NotBlank(message = "El idPromocion puede ser 0")//Puede no tener promocion
    private Long idPromocion;

    @NotNull(message = "El idCalificacion es obligatorio")
    private Long idCalificacion;

    @NotNull(message = "El idPago es obligatorio")
    private Long idPago;

    //Campos de este ms

    @NotNull(message = "La fecha de fecha de la reserva es obligatoria")
    private LocalDateTime fechaReserva;

    @NotNull(message = "El Precio de la reserva es obligatoria")
    private Double precioReserva;

    @NotBlank(message = "El descuento aplicado puede ser 0")
    private Double descuentoAplicado;

    @NotNull(message = "El precio final es obligatorio")
    private Double precioFinal;

    @NotNull(message = "El estado de la reserva es obligatorio")
    private EstadoReserva estadoReserva;
}