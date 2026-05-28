package com.ReservaPro.ms_reserva.service;

import com.ReservaPro.ms_reserva.exception.ReservaNoEncontrado;
import com.ReservaPro.ms_reserva.model.Reserva;
import com.ReservaPro.ms_reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository ReservaRepository;

    // obtenerTodasLasReserva
    public List<Reserva> obtenerReserva(){
        return ReservaRepository.findAll();
    }

    // obtenerReserva
    public Reserva obtenerReserva(Long idUsuarioBuscado){
        Reserva reserva = ReservaRepository.findById(idReservaBuscado)
                .orElseThrow(() -> new ReservaNoEncontrado(idUsuarioBuscado));
        return reserva;
    }

    // crearReserva
    public Reserva crearReserva(Reserva reserva){
        return ReservaRepository.save(reserva);
    }

    // eliminarReserva
    public boolean eliminarReserva(Long idReservaAEliminar){
        try{
            ReservaRepository.deleteById(idReservaAEliminar);
            return true;
        } catch (Error error){
            return false;
        }
    }
}





// actualizarReserva