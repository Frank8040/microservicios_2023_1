package com.example.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.catalogo.entity.Producto;
import com.example.catalogo.service.ProductoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoController {
  @Autowired
  private ProductoService productoService;

  @GetMapping()
  public List<Producto> listar() {
    List<Producto> productos = productoService.listar();
    for (Producto producto : productos) {
      producto.setCategoria(producto.getCategoria());
    }
    return productos;
  }

  @PostMapping()
  public Producto guardar(@RequestBody Producto producto) {
    producto.setCategoria(producto.getCategoria());
    return productoService.guardar(producto);
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