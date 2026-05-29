package com.ReservaPro.ms_usuario.service;

import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontrado;
import com.ReservaPro.ms_usuario.mapper.UsuarioMapper;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository userRepository;
    private final UsuarioMapper usuarioMapper;
    //Lista de usuario completa
    public List<Usuario> obtenerUsuarios(){
        return usuarioMapper.toResponseList(usuarioRepository) .findAll();
    }

    //Obtener usuario por id
    public Usuario obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontrado("Usuario no encontrado con ID: " + id));
    }
    //Creacion del usuario
    public Usuario crearUsuario(Usuario usuario){
        return userRepository.save(usuario);
    }

    //Eliminacion del usuario por id
    public boolean eliminarUsuario(Long idUsuarioAEliminar){
        try{
            userRepository.deleteById(idUsuarioAEliminar);
            return true;
        } catch (Error error){
            return false;
        }
    }


}
