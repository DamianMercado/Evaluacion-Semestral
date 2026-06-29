package com.ReservaPro.ms_cancelacion.client;
// Define el paquete donde se encuentra el cliente Feign

import org.springframework.cloud.openfeign.FeignClient;
// Permite crear clientes HTTP automáticamente para comunicarse con otros microservicios

import org.springframework.web.bind.annotation.GetMapping;
// Permite indicar que se realizará una petición HTTP GET

import org.springframework.web.bind.annotation.PathVariable;
// Permite recibir variables desde la URL

@FeignClient(name = "ms-reserva")
// Indica que este cliente se conectará al microservicio llamado "ms-reserva"
// El nombre debe ser igual al spring.application.name del microservicio reserva
// Eureka se encarga de localizar automáticamente el servicio

public interface ReservaClient {
    // Interfaz que define las operaciones remotas hacia ms-reserva

    @GetMapping("/api/v1/reservas/{id}")
        // Indica que se realizará una petición GET al endpoint:
        // /api/v1/reservas/{id}

    Object obtenerReservaPorId(@PathVariable("id") Long id);
    // Método que envía el ID de una reserva al microservicio ms-reserva
    // y obtiene la información de esa reserva
    //
    // @PathVariable("id") toma el valor del parámetro y lo reemplaza
    // en la URL: /api/v1/reservas/5
    //
    // Retorna un Object porque actualmente no existe un DTO específico
    // para la respuesta de ms-reserva
}