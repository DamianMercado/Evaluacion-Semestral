package com.ReservaPro.ms_promocion.service;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.exception.PromocionInactivaException;
import com.ReservaPro.ms_promocion.exception.PromocionNoEncontradaException;
import com.ReservaPro.ms_promocion.mapper.PromocionMapper;
import com.ReservaPro.ms_promocion.model.Promocion;
import com.ReservaPro.ms_promocion.repository.PromocionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromocionService {
    private final PromocionRepository promocionRepository;
    private final PromocionMapper promocionMapper;
    private static final Logger log = LoggerFactory.getLogger(PromocionService.class);


    @Transactional
    public List<Promocion> ListarPromociones() {
        log.info("Iniciando lista de promociones");
        return promocionRepository.findAll();
    }

    @Transactional
    public PromocionResponse crearPromocion(PromocionRequest promocion) {
        Promocion promocionAux = promocionMapper.toEntity(promocion);
        log.info("Creando Promoción {}", promocionAux);

        return promocionMapper.toResponse(promocionRepository.save(promocionAux));
    }

    @Transactional
    public CalcularDescuentoResponse calcularYActivarPromocion(String codigoPromocion, Double montoOriginal) {
        log.info("Se esta calculando el porcentaje de descuento con le monto para aplicar la promoción");
        Promocion promocionAux = promocionRepository.findByCodigoPromocion(codigoPromocion.toUpperCase()).orElseThrow(() ->
                new PromocionNoEncontradaException("El codigo de la promoción no exixte"));

        if (!promocionAux.getActivaPromocion()) {
            throw new PromocionInactivaException("La promocion no se puede activar");
        }

        Double porcentaje = promocionAux.getPorcentajeDescuento();
        Double descuentoAplicado = montoOriginal * (porcentaje / 100);
        Double montoFinal = montoOriginal - descuentoAplicado;

        //promocionAux.setActivaPromocion(false); <- ESTA LINEA ES PARA APLICAR EL DESCUENTO UNA SOLA VEZ

        CalcularDescuentoResponse respuesta = new CalcularDescuentoResponse();
        respuesta.setCodigoPromocion(promocionAux.getCodigoPromocion());
        respuesta.setMontoOriginal(montoOriginal);
        respuesta.setDescuentoAplicado(descuentoAplicado);
        respuesta.setMontoFinal(montoFinal);

        return respuesta;
    }

}
