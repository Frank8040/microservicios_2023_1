package com.example.venta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.venta.dto.Producto;

@FeignClient(name = "catalogo-service", path = "/producto")
public interface ProductoFeign {
    
    @GetMapping("/{id}")
    public Producto listById(@PathVariable(required = true) Integer id);
}
