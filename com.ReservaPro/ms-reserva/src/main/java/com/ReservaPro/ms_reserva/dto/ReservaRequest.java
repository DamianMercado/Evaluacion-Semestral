package com.ReservaPro.ms_reserva.dto;

public class ReservaRequest {
}




private String isbn;

@NotBlank(message = "El título es obligatorio")
@Size(min = 1, max = 255, message = "El título debe tener entre 1 y 255 caracteres")
private String titulo;

@NotBlank(message = "La editorial es obligatoria")
@Size(max = 100, message = "La editorial no puede superar los 100 caracteres")
private String editorial;

@NotNull(message = "El año de publicación es obligatorio")
@Min(value = 1450, message = "El año de publicación no puede ser anterior a 1450")
@Max(value = 2100, message = "El año de publicación no puede ser futuro lejano")
private Integer anioPublicacion;

@NotBlank(message = "El autor es obligatorio")
@Size(max = 150, message = "El nombre del autor no puede superar los 150 caracteres")
private String autor;