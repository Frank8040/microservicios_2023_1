package com.example.imagen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class ImagenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagenApplication.class, args);
	}

	@Bean
    public OpenAPI custoOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("OPEN API MICROSERVICIOS IMAGEN")
                .version("0.0.1")
                .description("servicio web imagen")
                .termsOfService("http://swagger.io/terms")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        );
    }

}
