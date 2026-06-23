package com.ReservaPro.ms_calificacion.mapper;

import com.ReservaPro.ms_calificacion.dto.request.UsuarioRequest;
import com.ReservaPro.ms_calificacion.dto.response.UsuarioResponse;
import com.ReservaPro.ms_calificacion.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel ="spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequest usuarioRequest);

    UsuarioResponse toResponse(Usuario usuario);

    List<UsuarioResponse> toResponseList (List<Usuario> usuarioList);
}