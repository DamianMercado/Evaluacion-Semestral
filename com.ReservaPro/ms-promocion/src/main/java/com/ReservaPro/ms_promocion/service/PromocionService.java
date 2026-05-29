package com.ReservaPro.ms_promocion.service;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.exception.PromocionException;
import com.ReservaPro.ms_promocion.mapper.PromocionMapper;
import com.ReservaPro.ms_promocion.model.Promocion;
import com.ReservaPro.ms_promocion.repository.PromocionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final PromocionMapper promocionMapper;
    private static final Logger log = LoggerFactory.getLogger(PromocionService.class);

    @Transactional
    public PromocionResponse crearPromocion(PromocionRequest promocion) {
        Promocion promocionAux = promocionMapper.toEntity(promocion);
        log.info("Creando Promocion {}", promocionAux);

        return promocionMapper.toResponse(promocionRepository.save(promocionAux));
    }

    @Transactional
    public CalcularDescuentoResponse calcularYActivarPromocion(String codigoPromocion, Double montoOriginal) {
        Promocion promocionAux = promocionRepository.findByCodigoPromocion(codigoPromocion.toUpperCase()).orElseThrow(() ->
                new RuntimeException("El codigo de la promoción no exixte"));

        if (!promocionAux.getActivaPromocion()) {
            throw new PromocionException("La promocion no se puede activar");
        }

        Double porcentaje = promocionAux.getPorcentajeDescuento();
        Double descuentoAplicado = montoOriginal * (porcentaje / 100);
        Double montoFinal = montoOriginal - descuentoAplicado;

        CalcularDescuentoResponse respuesta = new CalcularDescuentoResponse();

        respuesta.setCodigoPromocion(promocionAux.getCodigoPromocion());
        respuesta.setMontoOriginal(montoOriginal);
        respuesta.setDescuentoAplicado(descuentoAplicado);
        respuesta.setMontoFinal(montoFinal);

        return respuesta;
    }

}