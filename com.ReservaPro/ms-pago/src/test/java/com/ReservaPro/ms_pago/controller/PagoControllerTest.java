package com.ReservaPro.ms_pago.controller;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import com.ReservaPro.ms_pago.exception.GlobalExceptionHandler;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.exception.PagoNoReembolsadoException;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.service.PagoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PagoControllerTest {

    @Mock
    private PagoService pagoService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Pago pago;
    private PagoRequest request;
    private PagoResponse response;

    @BeforeEach
    void setUp() {
        PagoController controller = new PagoController(pagoService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        pago = Pago.builder()
                .id(1L)
                .idReserva(10L)
                .montoOriginal(10000.0)
                .montoPago(10000.0)
                .metodoPago("DEBITO")
                .tipoBanco(TipoBanco.BANCO_ESTADO)
                .estadoPago(Estado.PENDIENTE)
                .fechaPago(LocalDate.now())
                .aplicaDescuento(false)
                .build();

        request = new PagoRequest();
        request.setIdReserva(10L);
        request.setMontoOriginal(10000.0);
        request.setMontoPago(10000.0);
        request.setMetodoPago("DEBITO");
        request.setTipoBanco(TipoBanco.BANCO_ESTADO);

        response = new PagoResponse();
        response.setId(1L);
        response.setIdReserva(10L);
        response.setMontoOriginal(10000.0);
        response.setMontoPago(10000.0);
        response.setMetodoPago("DEBITO");
        response.setTipoBanco(TipoBanco.BANCO_ESTADO);
        response.setEstadoPago(Estado.PENDIENTE);
        response.setFechaPago(LocalDate.now());
        response.setDescuento("No aplica");
    }

    @Test
    void listarPagos_retorna200ConLista() throws Exception {
        when(pagoService.listarPagos()).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void obtenerPagosPorReserva_retorna200ConLista() throws Exception {
        when(pagoService.obtenerPagosPorReserva(10L)).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/v1/pagos/reserva/{idReserva}", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReserva").value(10L));
    }

    @Test
    void procesarPago_conDatosValidos_retorna201() throws Exception {
        when(pagoService.procesarPago(any(PagoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descuento").value("No aplica"));
    }

    @Test
    void procesarPago_conDatosInvalidos_retorna400() throws Exception {
        request.setMontoOriginal(null);
        request.setMetodoPago("");

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validarPago_cuandoExiste_retorna200() throws Exception {
        response.setEstadoPago(Estado.PAGADO);
        when(pagoService.validarPago(1L)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/pagos/validar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoPago").value("PAGADO"));
    }

    @Test
    void validarPago_cuandoNoExiste_retorna404() throws Exception {
        when(pagoService.validarPago(99L))
                .thenThrow(new PagoNoEncontradoException(99L));

        mockMvc.perform(patch("/api/v1/pagos/validar/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void validarPago_cuandoYaValidado_retorna409() throws Exception {
        when(pagoService.validarPago(1L))
                .thenThrow(new IllegalStateException("El pago ya está validado"));

        mockMvc.perform(patch("/api/v1/pagos/validar/{id}", 1L))
                .andExpect(status().isConflict());
    }

    @Test
    void reembolsarPago_cuandoEstaPagado_retorna200() throws Exception {
        response.setEstadoPago(Estado.REEMBOLSO);
        when(pagoService.reembolsarPago(1L)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/pagos/reembolsar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoPago").value("REEMBOLSO"));
    }

    @Test
    void reembolsarPago_cuandoNoEstaPagado_retorna400() throws Exception {
        when(pagoService.reembolsarPago(1L))
                .thenThrow(new PagoNoReembolsadoException("Solo se puede reembolsar un pago en estado PAGADO"));

        mockMvc.perform(patch("/api/v1/pagos/reembolsar/{id}", 1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarPago_cuandoExiste_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/pagos/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarPago_cuandoNoExiste_retorna404() throws Exception {
        org.mockito.Mockito.doThrow(new PagoNoEncontradoException(99L))
                .when(pagoService).eliminarPago(99L);

        mockMvc.perform(delete("/api/v1/pagos/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void verEstadoPago_retorna200() throws Exception {
        EstadoResponse estadoResponse = new EstadoResponse();
        estadoResponse.setId(1L);
        estadoResponse.setEstadoPago("PENDIENTE");

        when(pagoService.verEstadoPago(anyLong())).thenReturn(estadoResponse);

        mockMvc.perform(get("/api/v1/pagos/estado/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoPago").value("PENDIENTE"));
    }

    @Test
    void verMontoPago_retorna200() throws Exception {
        MontoResponse montoResponse = new MontoResponse();
        montoResponse.setId(1L);
        montoResponse.setMontoPago(8000.0);

        when(pagoService.verMontoPago(anyLong())).thenReturn(montoResponse);

        mockMvc.perform(get("/api/v1/pagos/monto/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoPago").value(8000.0));
    }
}
