package com.ReservaPro.ms_pago.dto.response;

import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoResponse {
    private Long id;
    private Long idReserva;
    private Double montoOriginal;
    private Double montoPago;
    private String metodoPago;
    private TipoBanco tipoBanco;
    private Estado estadoPago;
    private LocalDate fechaPago;
    private String descuento;
    private String codigoPromocion;
}