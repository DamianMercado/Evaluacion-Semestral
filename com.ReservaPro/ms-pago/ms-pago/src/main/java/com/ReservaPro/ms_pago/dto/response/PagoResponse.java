package com.ReservaPro.ms_pago.dto.response;

import com.ReservaPro.ms_pago.model.Metodo;
import com.ReservaPro.ms_pago.model.TipoBanco;
import com.ReservaPro.ms_pago.model.Estado;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoResponse {
    private Long idPago;
    private Double montoPago;
    private Metodo metodoPago;
    private TipoBanco tipoBanco;
    private Estado estadoPago;
    private LocalDate fechaPago;
    private String prueba;
}
