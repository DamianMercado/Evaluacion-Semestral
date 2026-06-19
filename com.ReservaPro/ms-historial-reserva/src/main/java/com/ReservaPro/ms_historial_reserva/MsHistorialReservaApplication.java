package com.ReservaPro.ms_historial_reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

@EnableFeignClients
public class MsHistorialReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHistorialReservaApplication.class, args);
	}

}
