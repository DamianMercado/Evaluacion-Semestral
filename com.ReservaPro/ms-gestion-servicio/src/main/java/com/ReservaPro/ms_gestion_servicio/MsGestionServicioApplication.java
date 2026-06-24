package com.ReservaPro.ms_gestion_servicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsGestionServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestionServicioApplication.class, args);
	}

}
