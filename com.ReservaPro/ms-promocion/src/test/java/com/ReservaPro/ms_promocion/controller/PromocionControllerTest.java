package com.ReservaPro.ms_promocion.controller;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.exception.GlobalExceptionHandler;
import com.ReservaPro.ms_promocion.exception.PromocionInactivaException;
import com.ReservaPro.ms_promocion.exception.PromocionNoEncontradaException;
import com.ReservaPro.ms_promocion.model.Promocion;
import com.ReservaPro.ms_promocion.service.PromocionService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PromocionControllerTest {

    @Mock
    private PromocionService promocionService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Promocion promocion;
    private PromocionRequest request;
    private PromocionResponse response;

    @BeforeEach
    void setUp() {
        PromocionController controller = new PromocionController(promocionService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        promocion = new Promocion();
        promocion.setId(1L);
        promocion.setCodigoPromocion("VERANO25");
        promocion.setDescripcion("Descuento de verano");
        promocion.setPorcentajeDescuento(25.0);
        promocion.setActivaPromocion(true);

        request = new PromocionRequest();
        request.setCodigoPromocion("verano25");
        request.setDescripcion("Descuento de verano");
        request.setPorcentajeDescuento(25.0);
        request.setActivaPromocion(true);

        response = new PromocionResponse();
        response.setId(1L);
        response.setCodigoPromocion("VERANO25");
        response.setDescripcion("Descuento de verano");
        response.setPorcentajeDescuento(25.0);
        response.setActivaPromocion(true);
    }

    @Test
    void obtenerTodasLasPromociones_retorna200ConLista() throws Exception {
        when(promocionService.ListarPromociones()).thenReturn(List.of(promocion));

        mockMvc.perform(get("/api/v1/promociones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoPromocion").value("VERANO25"));
    }

    @Test
    void crearPromocion_conDatosValidos_retorna201() throws Exception {
        when(promocionService.crearPromocion(any(PromocionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/promociones")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoPromocion").value("VERANO25"));
    }

    @Test
    void crearPromocion_conDatosInvalidos_retorna400() throws Exception {
        request.setDescripcion("");
        request.setPorcentajeDescuento(null);
        request.setActivaPromocion(null);

        mockMvc.perform(post("/api/v1/promociones")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores").exists());
    }

    @Test
    void calcularYActivarPromocion_cuandoExisteYActiva_retorna200() throws Exception {
        CalcularDescuentoResponse descuento = new CalcularDescuentoResponse();
        descuento.setCodigoPromocion("VERANO25");
        descuento.setMontoOriginal(1000.0);
        descuento.setDescuentoAplicado(250.0);
        descuento.setMontoFinal(750.0);

        when(promocionService.calcularYActivarPromocion(anyString(), anyDouble())).thenReturn(descuento);

        mockMvc.perform(post("/api/v1/promociones/aplicar")
                        .param("codigoPromocion", "verano25")
                        .param("montoFinal", "1000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoFinal").value(750.0));
    }

    @Test
    void calcularYActivarPromocion_cuandoNoExiste_retorna400() throws Exception {
        when(promocionService.calcularYActivarPromocion(anyString(), anyDouble()))
                .thenThrow(new PromocionNoEncontradaException("El codigo de la promoción no exixte"));

        mockMvc.perform(post("/api/v1/promociones/aplicar")
                        .param("codigoPromocion", "noexiste")
                        .param("montoFinal", "100.0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El codigo de la promoción no exixte"));
    }

    @Test
    void calcularYActivarPromocion_cuandoEstaInactiva_retorna400() throws Exception {
        when(promocionService.calcularYActivarPromocion(anyString(), anyDouble()))
                .thenThrow(new PromocionInactivaException("La promocion no se puede activar"));

        mockMvc.perform(post("/api/v1/promociones/aplicar")
                        .param("codigoPromocion", "invierno10")
                        .param("montoFinal", "100.0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("La promocion no se puede activar"));
    }
}
