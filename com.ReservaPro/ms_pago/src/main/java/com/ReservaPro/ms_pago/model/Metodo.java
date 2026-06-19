package com.ReservaPro.ms_pago.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Metodo {
    CREDITO,
    DEBITO,
    EFECTIVO
}
