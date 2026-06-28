package com.ReservaPro.ms_notificacion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
// este  sirve para capturara errores de el microservicio
    //
    @ExceptionHandler(NotificacionNoEncontradaException.class)
    public ResponseEntity<?> handleNotificacionNoEncontrada(NotificacionNoEncontradaException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());//guarda fecha hora del error
        response.put("status", 404);//guarda codigo http no encontrado
        response.put("error", ex.getMessage());// guarda mensaje del error

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}             // devuelve la respuesta al cliente