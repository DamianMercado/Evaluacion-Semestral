package com.ReservaPro.ms_reserva.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta con información completa de una reserva (incluye datos de otros MS)")
public class ReservaCompletaResponse {

    private ReservaResponse reserva;
    private Object usuario;
    private Object servicio;
    private Object pago;
    private Object calificacion;
}