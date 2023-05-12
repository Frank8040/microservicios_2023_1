package com.example.imagen.controller;

import com.example.imagen.service.ImagenService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.imagen.entity.Imagen;

@RestController
@RequestMapping("/imagen")
public class ImagenController {
    @Autowired
    private ImagenService clienteService;

    @GetMapping()
    public ResponseEntity<List<Imagen>> list() {
        return ResponseEntity.ok().body(clienteService.listar());
    }

    @PostMapping()
    public ResponseEntity<Imagen> save(@RequestBody Imagen imagen) {
        return ResponseEntity.ok(clienteService.guardar(imagen));
    }

    @PutMapping()
    public ResponseEntity<Imagen> update(@RequestBody Imagen imagen) {
        return ResponseEntity.ok(clienteService.actualizar(imagen));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(clienteService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        clienteService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }
}
