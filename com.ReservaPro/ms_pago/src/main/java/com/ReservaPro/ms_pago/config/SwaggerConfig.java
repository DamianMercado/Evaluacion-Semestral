package com.ReservaPro.ms_pago.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI () {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 Sistema de pagos")
                        .version("1.0")
                        .description("Documentación de la API sistema de pagos"));

    }

}
