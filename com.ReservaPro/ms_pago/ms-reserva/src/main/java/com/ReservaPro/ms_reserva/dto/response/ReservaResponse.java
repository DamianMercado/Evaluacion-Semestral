package com.ReservaPro.ms_reserva.dto.response;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaResponse {
    private Long id;
    private Long idUsuario;
    private Long idGestionServicio;
    private Long idPromocion;
    private Long idPago;
    private Long idCalificacion;
    private LocalDateTime fechaReserva;
    private Double precioReserva;
    private Double descuentoAplicado;
    private Double precioFinal;
    private EstadoReserva estadoReserva;
}
