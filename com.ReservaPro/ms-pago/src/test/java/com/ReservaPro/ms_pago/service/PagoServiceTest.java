package com.ReservaPro.ms_pago.service;

import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoService pagoService;
    @Test
    void deberiaCrearPago() {
        Pago pago = new Pago();
    }
}
