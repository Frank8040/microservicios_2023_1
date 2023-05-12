package com.example.imagen.controller;

import com.example.imagen.service.ImagenService;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.imagen.entity.Imagen;

@RestController
@RequestMapping("/imagen")
public class ImagenController {
    @Autowired
    private ImagenService imagenService;

    @GetMapping
    public ResponseEntity<List<Imagen>> list() {
        List<Imagen> imagenes = imagenService.listar();
        return ResponseEntity.ok(imagenes);
    }

    @PostMapping
    public ResponseEntity<Imagen> save(@RequestBody Imagen imagen) {
        Imagen nuevaImagen = imagenService.guardar(imagen);
        return ResponseEntity.ok(nuevaImagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagen> update(@PathVariable("id") Integer id, @RequestBody Imagen imagen) {
        Optional<Imagen> imagenExistente = imagenService.listarPorId(id);
        if (imagenExistente.isPresent()) {
            imagen.setId(id);
            Imagen imagenActualizada = imagenService.actualizar(imagen);
            return ResponseEntity.ok(imagenActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> listById(@PathVariable("id") Integer id) {
        Optional<Imagen> imagen = imagenService.listarPorId(id);
        if (imagen.isPresent()) {
            return ResponseEntity.ok(imagen.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {
        Optional<Imagen> imagenExistente = imagenService.listarPorId(id);
        if (imagenExistente.isPresent()) {
            imagenService.eliminarPorId(id);
            return ResponseEntity.ok("Eliminación correcta");
        }
        return ResponseEntity.notFound().build();
    }
}
