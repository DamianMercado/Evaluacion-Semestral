
package com.ReservaPro.ms_pago.service;
import com.ReservaPro.ms_pago.client.PromocionClient;
import com.ReservaPro.ms_pago.client.ReservaClient;
import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.request.ReservaPagoRequest;
import com.ReservaPro.ms_pago.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.exception.PagoNoReembolsadoException;
import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
        private PagoRepository pagoRepository;

        @Mock
        private PagoMapper pagoMapper;

        @Mock
        private PromocionClient promocionClient;

        @Mock
        private ReservaClient reservaClient;

        @InjectMocks
        private PagoService pagoService;

        private Pago pago;
        private PagoRequest request;
        private PagoResponse response;

        @BeforeEach
        void prepararDatos() {

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
                    .codigoPromocion(null)
                    .build();

            request = new PagoRequest();
            request.setIdReserva(10L);
            request.setMontoOriginal(10000.0);
            request.setMontoPago(10000.0);
            request.setMetodoPago("DEBITO");
            request.setTipoBanco(TipoBanco.BANCO_ESTADO);
            request.setCodigoPromocion(null);

            response = new PagoResponse();
            response.setId(1L);
            response.setIdReserva(10L);
            response.setMontoOriginal(10000.0);
            response.setMontoPago(10000.0);
            response.setMetodoPago("DEBITO");
            response.setTipoBanco(TipoBanco.BANCO_ESTADO);
            response.setEstadoPago(Estado.PENDIENTE);
            response.setFechaPago(LocalDate.now());
            response.setCodigoPromocion(null);
        }

        @Test
        void listarPagosDebeRetornarLista() {

            Pago pago2 = new Pago();
            pago2.setId(2L);

            when(pagoRepository.findAll())
                    .thenReturn(List.of(pago, pago2));

            List<Pago> resultado =
                    pagoService.listarPagos();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());

            verify(pagoRepository).findAll();
        }

        @Test
        void procesarPagoSinPromocionDebeGuardarPago() {

            when(pagoMapper.toEntity(request))
                    .thenReturn(pago);

            when(pagoRepository.save(pago))
                    .thenReturn(pago);

            when(pagoMapper.toResponse(pago))
                    .thenReturn(response);

            PagoResponse resultado =
                    pagoService.procesarPago(request);

            assertNotNull(resultado);
            assertEquals("No aplica", resultado.getDescuento());
            assertEquals(Estado.PENDIENTE, pago.getEstadoPago());
            assertFalse(pago.isAplicaDescuento());
            assertEquals(10000.0, pago.getMontoPago());

            verify(promocionClient, never())
                    .aplicarPromocion(anyString(), anyDouble());

            verify(pagoMapper).toEntity(request);
            verify(pagoRepository).save(pago);
            verify(pagoMapper).toResponse(pago);
        }

        @Test
        void procesarPagoConPromocionDebeAplicarDescuento() {

            request.setCodigoPromocion("PROMO20");

            CalcularDescuentoResponse descuento =
                    new CalcularDescuentoResponse();

            descuento.setCodigoPromocion("PROMO20");
            descuento.setMontoOriginal(10000.0);
            descuento.setDescuentoAplicado(2000.0);
            descuento.setMontoFinal(8000.0);

            when(promocionClient.aplicarPromocion(
                    "PROMO20",
                    10000.0
            )).thenReturn(descuento);

            when(pagoMapper.toEntity(request))
                    .thenReturn(pago);

            when(pagoRepository.save(pago))
                    .thenReturn(pago);

            when(pagoMapper.toResponse(pago))
                    .thenReturn(response);

            PagoResponse resultado =
                    pagoService.procesarPago(request);

            assertNotNull(resultado);
            assertEquals("Si aplica (20%)", resultado.getDescuento());
            assertEquals(8000.0, pago.getMontoPago());
            assertEquals("PROMO20", pago.getCodigoPromocion());
            assertTrue(pago.isAplicaDescuento());
            assertEquals(Estado.PENDIENTE, pago.getEstadoPago());

            verify(promocionClient)
                    .aplicarPromocion("PROMO20", 10000.0);

            verify(pagoRepository).save(pago);
        }

        @Test
        void procesarPagoConCodigoVacioNoDebeAplicarPromocion() {

            request.setCodigoPromocion(" ");

            when(pagoMapper.toEntity(request))
                    .thenReturn(pago);

            when(pagoRepository.save(pago))
                    .thenReturn(pago);

            when(pagoMapper.toResponse(pago))
                    .thenReturn(response);

            PagoResponse resultado =
                    pagoService.procesarPago(request);

            assertNotNull(resultado);
            assertEquals("No aplica", resultado.getDescuento());
            assertFalse(pago.isAplicaDescuento());

            verify(promocionClient, never())
                    .aplicarPromocion(anyString(), anyDouble());
        }

        @Test
        void procesarPagoConMontoCeroDebeLanzarExcepcion() {

            request.setMontoPago(0.0);

            IllegalArgumentException excepcion =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> pagoService.procesarPago(request)
                    );

            assertEquals(
                    "El monto final debe ser mayor a cero",
                    excepcion.getMessage()
            );

            verify(pagoRepository, never())
                    .save(any(Pago.class));
        }

        @Test
        void validarPagoPendienteDebeCambiarEstadoAPagado() {

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            when(pagoRepository.save(pago))
                    .thenReturn(pago);

            when(pagoMapper.toResponse(pago))
                    .thenReturn(response);

            PagoResponse resultado =
                    pagoService.validarPago(1L);

            assertNotNull(resultado);
            assertEquals(Estado.PAGADO, pago.getEstadoPago());

            verify(pagoRepository).findById(1L);
            verify(pagoRepository).save(pago);

            verify(reservaClient).confirmarPagoReserva(
                    eq(10L),
                    any(ReservaPagoRequest.class)
            );

            verify(pagoMapper).toResponse(pago);
        }

        @Test
        void validarPagoInexistenteDebeLanzarExcepcion() {

            when(pagoRepository.findById(99L))
                    .thenReturn(Optional.empty());

            assertThrows(
                    PagoNoEncontradoException.class,
                    () -> pagoService.validarPago(99L)
            );

            verify(pagoRepository, never())
                    .save(any(Pago.class));
        }

        @Test
        void validarPagoPagadoDebeLanzarExcepcion() {

            pago.setEstadoPago(Estado.PAGADO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            IllegalStateException excepcion =
                    assertThrows(
                            IllegalStateException.class,
                            () -> pagoService.validarPago(1L)
                    );

            assertEquals(
                    "El pago ya está validado",
                    excepcion.getMessage()
            );

            verify(pagoRepository, never())
                    .save(any(Pago.class));
        }

        @Test
        void validarPagoReembolsadoDebeLanzarExcepcion() {

            pago.setEstadoPago(Estado.REEMBOLSO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            IllegalStateException excepcion =
                    assertThrows(
                            IllegalStateException.class,
                            () -> pagoService.validarPago(1L)
                    );

            assertEquals(
                    "No se puede validar un pago reembolsado",
                    excepcion.getMessage()
            );

            verify(pagoRepository, never())
                    .save(any(Pago.class));
        }

        @Test
        void notificarReservaDebeLlamarReservaClient() {

            pagoService.notificarReserva(pago);

            verify(reservaClient).confirmarPagoReserva(
                    eq(10L),
                    any(ReservaPagoRequest.class)
            );
        }

        @Test
        void eliminarPagoPendienteDebeEliminarPago() {

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            pagoService.eliminarPago(1L);

            verify(pagoRepository).findById(1L);
            verify(pagoRepository).delete(pago);
        }

        @Test
        void eliminarPagoInexistenteDebeLanzarExcepcion() {

            when(pagoRepository.findById(99L))
                    .thenReturn(Optional.empty());

            assertThrows(
                    PagoNoEncontradoException.class,
                    () -> pagoService.eliminarPago(99L)
            );

            verify(pagoRepository, never())
                    .delete(any(Pago.class));
        }

        @Test
        void eliminarPagoPagadoDebeLanzarExcepcion() {

            pago.setEstadoPago(Estado.PAGADO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            assertThrows(
                    IllegalStateException.class,
                    () -> pagoService.eliminarPago(1L)
            );

            verify(pagoRepository, never())
                    .delete(any(Pago.class));
        }

        @Test
        void eliminarPagoReembolsadoDebeLanzarExcepcion() {

            pago.setEstadoPago(Estado.REEMBOLSO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            assertThrows(
                    IllegalStateException.class,
                    () -> pagoService.eliminarPago(1L)
            );

            verify(pagoRepository, never())
                    .delete(any(Pago.class));
        }

        @Test
        void reembolsarPagoPagadoDebeCambiarEstadoAReembolso() {

            pago.setEstadoPago(Estado.PAGADO);
            response.setEstadoPago(Estado.REEMBOLSO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            when(pagoRepository.save(pago))
                    .thenReturn(pago);

            when(pagoMapper.toResponse(pago))
                    .thenReturn(response);

            PagoResponse resultado =
                    pagoService.reembolsarPago(1L);

            assertNotNull(resultado);
            assertEquals(Estado.REEMBOLSO, pago.getEstadoPago());
            assertEquals(
                    Estado.REEMBOLSO,
                    resultado.getEstadoPago()
            );

            verify(pagoRepository).save(pago);
            verify(pagoMapper).toResponse(pago);
        }

        @Test
        void reembolsarPagoPendienteDebeLanzarExcepcion() {

            pago.setEstadoPago(Estado.PENDIENTE);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            PagoNoReembolsadoException excepcion =
                    assertThrows(
                            PagoNoReembolsadoException.class,
                            () -> pagoService.reembolsarPago(1L)
                    );

            assertEquals(
                    "Solo se puede reembolsar un pago en estado PAGADO",
                    excepcion.getMessage()
            );

            verify(pagoRepository, never())
                    .save(any(Pago.class));
        }

        @Test
        void verEstadoPagoDebeRetornarEstado() {

            pago.setEstadoPago(Estado.PAGADO);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            EstadoResponse resultado =
                    pagoService.verEstadoPago(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals(
                    Estado.PAGADO.getValor(),
                    resultado.getEstadoPago()
            );

            verify(pagoRepository).findById(1L);
        }

        @Test
        void verMontoPagoDebeRetornarMonto() {

            pago.setMontoPago(8000.0);

            when(pagoRepository.findById(1L))
                    .thenReturn(Optional.of(pago));

            MontoResponse resultado =
                    pagoService.verMontoPago(1L);

            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals(8000.0, resultado.getMontoPago());

            verify(pagoRepository).findById(1L);
        }

        @Test
        void obtenerPagosPorReservaDebeRetornarLista() {

            when(pagoRepository.findByIdReserva(10L))
                    .thenReturn(List.of(pago));

            List<Pago> resultado =
                    pagoService.obtenerPagosPorReserva(10L);

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(10L, resultado.get(0).getIdReserva());

            verify(pagoRepository).findByIdReserva(10L);
        }
    }


