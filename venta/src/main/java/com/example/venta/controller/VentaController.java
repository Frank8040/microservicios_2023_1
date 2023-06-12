package com.example.venta.controller;

import com.example.venta.service.VentaService;
import com.example.venta.entity.Venta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping()
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok().body(ventaService.listar());
    }

    @PostMapping()
    public ResponseEntity<Venta> guardar(@RequestBody Venta Venta) {
        return ResponseEntity.ok(ventaService.guardar(Venta));
    }

    @PutMapping()
    public ResponseEntity<Venta> actualizar(@RequestBody Venta Venta) {
        return ResponseEntity.ok(ventaService.actualizar(Venta));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(ventaService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id) {
        ventaService.eliminarPorId(id);
        return "Eliminación Correcta";
    }
}

