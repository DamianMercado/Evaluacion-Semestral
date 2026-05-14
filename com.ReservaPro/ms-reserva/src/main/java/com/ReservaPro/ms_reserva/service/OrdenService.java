package com.ReservaPro.ms_reserva.service;

import com.ReservaPro.ms_reserva.exception.OrdenNoEncontrado;
import com.ReservaPro.ms_reserva.model.Orden;
import com.ReservaPro.ms_reserva.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository userRepository;

    public List<Orden> obtenerUsuarios(){
        return userRepository.findAll();
    }

    public Orden obtenerUsuario(Long idUsuarioBuscado){
        Orden orden = userRepository.findById(idUsuarioBuscado)
                .orElseThrow(() -> new OrdenNoEncontrado(idUsuarioBuscado));
        return orden;
    }

    public Orden crearUsuario(Orden orden){
        return userRepository.save(orden);
    }

    public boolean eliminarUsuario(Long idUsuarioAEliminar){
        try{
            userRepository.deleteById(idUsuarioAEliminar);
            return true;
        } catch (Error error){
            return false;
        }
    }
}
