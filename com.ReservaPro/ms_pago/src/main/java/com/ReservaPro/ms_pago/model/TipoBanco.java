package com.ReservaPro.ms_pago.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoBanco {
    BANCO_ESTADO,
    FALABELLA,
    CHILE,
    SANTANDER,
    BCI,
    TENPO
}
