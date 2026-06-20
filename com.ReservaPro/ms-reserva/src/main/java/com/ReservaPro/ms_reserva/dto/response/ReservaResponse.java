package com.ReservaPro.ms_reserva.dto.response;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ReservaResponse {

//Id Reserva

    private Long id;

//Datos otros ms

    private Long idUsuario;
    private Long idGestionServicio;
    private Long idPromocion;
    private Long idPago;
    private Long idCalificacion;

//Datos Reserva

    private LocalDate fechaReserva;
    private Double precioReserva;
    private Double descuentoAplicado;
    private Double precioFinal;
    private EstadoReserva estadoReserva;
}
