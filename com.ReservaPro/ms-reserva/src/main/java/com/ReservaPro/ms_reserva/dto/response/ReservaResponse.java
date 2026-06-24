package com.ReservaPro.ms_reserva.dto.response;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaResponse {

    private Long id;
    private Long idUsuario;
    private Long idGestionServicio;
    private Long idPromocion;
    private Long idCalificacion;
    private Long idPago;

    private LocalDateTime fechaReserva;
    private Double precioReserva;
    private Double descuentoAplicado;
    private Double precioFinal;
    private String estadoReserva;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}