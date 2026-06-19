package com.ReservaPro.ms_reserva.dto.request;

import jakarta.validation.constraints.NotNull;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaRequest {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID del servicio es obligatorio")
    private Long idGestionServicio;

    private Long idPromocion;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDateTime fechaReserva;
}