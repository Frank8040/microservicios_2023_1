package com.example.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class CatalogoApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(CatalogoApplication.class, args);
  }

  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:src/main/resources/public/imagenes/");
    }

  @Bean
  public OpenAPI custoOpenAPI() {
    return new OpenAPI().info(new Info()
        .title("OPEN API MICROSERVICIOS CATALOGO")
        .version("0.0.1")
        .description("servicio web catalogo")
        .termsOfService("http://swagger.io/terms")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}
