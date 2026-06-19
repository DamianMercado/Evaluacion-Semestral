package com.ReservaPro.ms_usuario.mapper;

import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel ="spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioRequest usuarioRequest);
    UsuarioResponse toResponse(Usuario usuario);
    List<UsuarioResponse> toResponseList (List<Usuario> usuarioList);
}