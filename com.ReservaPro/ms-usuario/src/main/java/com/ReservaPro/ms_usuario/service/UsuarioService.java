package com.ReservaPro.ms_usuario.service;

import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontrado;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository userRepository;

    public List<Usuario> obtenerUsuarios(){
        return userRepository.findAll();
    }

    public Usuario obtenerUsuario(Long idUsuarioBuscado){
        Usuario usuario = userRepository.findById(idUsuarioBuscado)
                .orElseThrow(() -> new UsuarioNoEncontrado(idUsuarioBuscado));
        return usuario;
    }

    public Usuario crearUsuario(Usuario usuario){
        return userRepository.save(usuario);
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
