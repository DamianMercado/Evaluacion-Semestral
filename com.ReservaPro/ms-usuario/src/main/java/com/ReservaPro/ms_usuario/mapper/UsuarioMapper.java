package com.ReservaPro.ms_usuario.mapper;
import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.model.Usuario;
import org.springframework.stereotype.Component;
@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        if (request == null) return null;
        return Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .rol(RolUsuario.valueOf(request.getRol().toUpperCase()))
                .build();
    }

    public UsuarioResponse toResponse(Usuario entity) {
        if (entity == null) return null;
        UsuarioResponse response = new UsuarioResponse();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setEmail(entity.getEmail());
        response.setRol(entity.getRol().name());
        return response;
    }
}
