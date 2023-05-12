package com.example.catalogo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.catalogo.dto.Imagen;

@FeignClient(name = "imagen-service", path = "/imagen" )
public interface ImagenFeign {
    @GetMapping("/{id}")
    public ResponseEntity<Imagen> listById(@PathVariable(required = true) Integer id);
}
