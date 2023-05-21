package com.example.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.catalogo.entity.Categoria;
import com.example.catalogo.entity.Producto;
import com.example.catalogo.service.CategoriaService;
import com.example.catalogo.service.ProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/producto")
public class ProductoController {
  @Autowired
  private ProductoService productoService;
  @Autowired
  private CategoriaService categoriaService;

  @GetMapping()
  public List<Producto> listar() {
    List<Producto> productos = productoService.listar();
    for (Producto producto : productos) {
      producto.setCategoria(producto.getCategoria());
    }
    return productos;
  }

  /*
   * @PostMapping()
   * public Producto guardar(@RequestBody Producto producto) {
   * producto.setCategoria(producto.getCategoria());
   * return productoService.guardar(producto);
   * }
   */

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> guardar(
      @RequestParam("nombre") String nombre,
      @RequestParam("categoria") Integer categoria,
      @RequestPart("file") MultipartFile file) {
    try {

      if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("No se ha enviado ninguna imagen.");
      }

      String directorioDestino = "src/main/resources/picture/";

      File carpetaDestino = new File(directorioDestino);
      if (!carpetaDestino.exists()) {
        carpetaDestino.mkdirs();
      }

      String rutaDestino = directorioDestino + file.getOriginalFilename();
      File destino = new File(rutaDestino);
      file.transferTo(destino);

      Optional<Categoria> categoriaOptional = categoriaService.listarPorId(categoria);
      if (categoriaOptional.isPresent()) {
        Categoria category = categoriaOptional.get();

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(category);
        producto.setImagen(rutaDestino);

        productoService.guardar(producto);

        return ResponseEntity.ok("Producto y imagen guardados correctamente.");
      } else {
        return ResponseEntity.badRequest().body("La categoría no existe.");
      }
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen.");
    }
  }

  @GetMapping("/{id}")
  public Producto buscarPorId(@PathVariable(required = true) Integer id) {
    Producto producto = productoService.listarPorId(id).get();
    producto.setCategoria(producto.getCategoria());
    return producto;
  }

  @PutMapping()
  public Producto actualizar(@RequestBody Producto producto) {
    producto.setCategoria(producto.getCategoria());
    return productoService.actualizar(producto);
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable(required = true) Integer id) {
    productoService.eliminarPorId(id);
  }
}