package com.ReservaPro.ms_pago.service;

import com.ReservaPro.ms_pago.client.PagosClient;
import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import com.ReservaPro.ms_pago.repository.PagoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @Mock
    private PagosClient pagosClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void listarPagos_retornaListaDePagos() {
        //Given
        Pago pago1 = new Pago();
        Pago pago2 = new Pago();
        when(pagoRepository.findAll()).thenReturn(List.of(pago1, pago2));

        //When
        List<Pago> resultado = pagoService.listarPagos();

        //Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pagoRepository).findAll();
    }

    @Test
    void procesarPago_sinDescuento() {
        //Given
        PagoRequest request = crearPagoRequest(100.0, null);
        Pago pagoEntity = new Pago();
        Pago pagoGuardado = new Pago();
        PagoResponse response = new PagoResponse();

        when(pagoMapper.toEntity(request)).thenReturn(pagoEntity);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);
        when(pagoMapper.toResponse(pagoGuardado)).thenReturn(response);

        //When
        PagoResponse resultado = pagoService.procesarPago(request);

        //Then
        assertNotNull(resultado);
        assertEquals("No aplication", resultado.getDescuento());
        verify(pagosClient, never()).calcularYActivarPromocion(anyString(), anyDouble());
    }

    @Test
    void procesarPago_conCodigoPromocional (){
        //Given
        String codigoPromocional = "1";
        PagoRequest request = crearPagoRequest(100.0, codigoPromocional);
        CalcularDescuentoResponse mockDescuentoResponse = new  CalcularDescuentoResponse();
        mockDescuentoResponse.setMontoFinal(80.0);
        mockDescuentoResponse.setDescuentoAplicado(20.0);

        Pago pagoEntity = new Pago();
        Pago pagoGuardado = new Pago();
        PagoResponse responseMock = new PagoResponse();

        when(pagosClient.calcularYActivarPromocion(codigoPromocional, 100.0)).thenReturn(mockDescuentoResponse);
        when(pagoMapper.toEntity(request)).thenReturn(pagoEntity);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);
        when(pagoMapper.toResponse(pagoGuardado)).thenReturn(responseMock);

        //When
        PagoResponse resultado = pagoService.procesarPago(request);

        //Then
        assertNotNull(resultado);
        assertEquals("Si aplica (20%)", resultado.getDescuento());
        verify(pagosClient).calcularYActivarPromocion(codigoPromocional, 100.0);
    }

    @Test
    void procesarPago_montoInvalido_lanzaExcepcion(){
        //Given
        PagoRequest request = crearPagoRequest(0.0, null);

        //When
        assertThrows(PagoNoEncontradoException.class, () -> pagoService.procesarPago(request));
        verify(pagoRepository, never()).save(any());
    }

    @Test
    void eliminarPago_ejecutaDeleteById() {
        //Given
        Long idPago = 1L;

        //When
        pagoService.eliminarPago(idPago);

        //Then
        verify(pagoRepository).deleteById(idPago);
    }

    private PagoRequest crearPagoRequest(Double monto, String codigoPromocional) {
        PagoRequest request = new PagoRequest();
        request.setMontoPago(monto);

        request.setMetodoPago("DEBITO");
        request.setTipoBanco(TipoBanco.BANCO_ESTADO);
        request.setEstadoPago(Estado.PENDIENTE);
        request.setFechaPago(LocalDate.now());
        request.setCodigoPromocion(codigoPromocional);
        request.setDescuento(0.0);

        return request;
    }
}
