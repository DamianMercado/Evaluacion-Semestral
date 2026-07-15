package com.ReservaPro.ms_promocion.service;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.exception.PromocionInactivaException;
import com.ReservaPro.ms_promocion.exception.PromocionNoEncontradaException;
import com.ReservaPro.ms_promocion.mapper.PromocionMapper;
import com.ReservaPro.ms_promocion.model.Promocion;
import com.ReservaPro.ms_promocion.repository.PromocionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @Mock
    private PromocionMapper promocionMapper;

    @InjectMocks
    private PromocionService promocionService;

    private Promocion promocion;

    @BeforeEach
    void setUp() {
        promocion = crearPromocion(1L, "VERANO25", 25.0, true);
    }

    // ---------- ListarPromociones ----------

    @Test
    void listarPromociones_retornaListaDeRepositorio() {
        List<Promocion> promociones = List.of(promocion);
        when(promocionRepository.findAll()).thenReturn(promociones);

        List<Promocion> resultado = promocionService.ListarPromociones();

        assertEquals(1, resultado.size());
        assertEquals(promocion, resultado.get(0));
        verify(promocionRepository).findAll();
    }

    @Test
    void listarPromociones_cuandoNoHayPromociones_retornaListaVacia() {
        when(promocionRepository.findAll()).thenReturn(List.of());

        List<Promocion> resultado = promocionService.ListarPromociones();

        assertTrue(resultado.isEmpty());
    }

    // ---------- crearPromocion ----------

    @Test
    void crearPromocion_mapeaGuardaYRetornaRespuesta() {
        PromocionRequest request = crearRequest("verano25", "Descuento de verano", 25.0, true);
        PromocionResponse response = crearResponse(1L, "VERANO25", 25.0, true);

        when(promocionMapper.toEntity(request)).thenReturn(promocion);
        when(promocionRepository.save(promocion)).thenReturn(promocion);
        when(promocionMapper.toResponse(promocion)).thenReturn(response);

        PromocionResponse resultado = promocionService.crearPromocion(request);

        assertNotNull(resultado);
        assertEquals("VERANO25", resultado.getCodigoPromocion());
        assertEquals(25.0, resultado.getPorcentajeDescuento());
        assertTrue(resultado.getActivaPromocion());
        verify(promocionRepository).save(promocion);
    }

    // ---------- calcularYActivarPromocion ----------

    @Test
    void calcularYActivarPromocion_cuandoExisteYEstaActiva_calculaDescuentoCorrectamente() {
        when(promocionRepository.findByCodigoPromocion("VERANO25"))
                .thenReturn(Optional.of(promocion));

        CalcularDescuentoResponse resultado =
                promocionService.calcularYActivarPromocion("verano25", 1000.0);

        assertNotNull(resultado);
        assertEquals("VERANO25", resultado.getCodigoPromocion());
        assertEquals(1000.0, resultado.getMontoOriginal());
        assertEquals(250.0, resultado.getDescuentoAplicado());
        assertEquals(750.0, resultado.getMontoFinal());
    }

    @Test
    void calcularYActivarPromocion_convierteCodigoAMayusculasParaBuscar() {
        when(promocionRepository.findByCodigoPromocion("VERANO25"))
                .thenReturn(Optional.of(promocion));

        promocionService.calcularYActivarPromocion("verano25", 100.0);

        verify(promocionRepository).findByCodigoPromocion("VERANO25");
    }

    @Test
    void calcularYActivarPromocion_cuandoNoExiste_lanzaException() {
        when(promocionRepository.findByCodigoPromocion("NOEXISTE"))
                .thenReturn(Optional.empty());

        assertThrows(PromocionNoEncontradaException.class,
                () -> promocionService.calcularYActivarPromocion("noexiste", 100.0));
    }

    @Test
    void calcularYActivarPromocion_cuandoEstaInactiva_lanzaException() {
        Promocion inactiva = crearPromocion(2L, "INVIERNO10", 10.0, false);
        when(promocionRepository.findByCodigoPromocion("INVIERNO10"))
                .thenReturn(Optional.of(inactiva));

        assertThrows(PromocionInactivaException.class,
                () -> promocionService.calcularYActivarPromocion("invierno10", 100.0));
    }

    // ---------- helpers ----------

    private Promocion crearPromocion(Long id, String codigo, Double porcentaje, boolean activa) {
        Promocion p = new Promocion();
        p.setId(id);
        p.setCodigoPromocion(codigo);
        p.setDescripcion("Descuento de verano");
        p.setPorcentajeDescuento(porcentaje);
        p.setActivaPromocion(activa);
        return p;
    }

    private PromocionRequest crearRequest(String codigo, String descripcion, Double porcentaje, boolean activa) {
        PromocionRequest r = new PromocionRequest();
        r.setCodigoPromocion(codigo);
        r.setDescripcion(descripcion);
        r.setPorcentajeDescuento(porcentaje);
        r.setActivaPromocion(activa);
        return r;
    }

    private PromocionResponse crearResponse(Long id, String codigo, Double porcentaje, boolean activa) {
        PromocionResponse r = new PromocionResponse();
        r.setId(id);
        r.setCodigoPromocion(codigo);
        r.setDescripcion("Descuento de verano");
        r.setPorcentajeDescuento(porcentaje);
        r.setActivaPromocion(activa);
        return r;
    }
}
