package com.example.catalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.catalogo.entity.Producto;
import com.example.catalogo.service.ProductoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/producto")
public class ProductoController {

  private final String UPLOAD_DIR = "src/main/resources/public/imagenes";

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

  /*@PostMapping()
  public Producto guardar(@RequestBody Producto producto) {
    producto.setCategoria(producto.getCategoria());
    return productoService.guardar(producto);
  }*/

  @PostMapping()
  public ResponseEntity<String> guardar(@ModelAttribute Producto producto, @RequestParam("file") MultipartFile file) {

    if (file.isEmpty()) {
      System.err.println("No se ha proporcionado un archivo adjunto válido.");
      return ResponseEntity.badRequest().build();
    }

    try {
      String fileExtension = getFileExtension(file.getOriginalFilename());
      if (!isValidImageExtension(fileExtension)) {
        return ResponseEntity.badRequest().body("Solo se permiten archivos con extensiones .png, .jpg y .jpeg");
      }
      String filename = UUID.randomUUID().toString();
      String newFileName = filename + fileExtension;
      Path filePath = Path.of(UPLOAD_DIR, newFileName);

      String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      String fileUrl = baseUri + "/imagenes/" + newFileName;
      producto.setImagen(fileUrl);
      producto.setCategoria(producto.getCategoria());

      productoService.guardar(producto);

      return ResponseEntity.ok(fileUrl);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private boolean isValidImageExtension(String fileExtension) {
    return fileExtension.equalsIgnoreCase(".png") ||
        fileExtension.equalsIgnoreCase(".jpg") ||
        fileExtension.equalsIgnoreCase(".jpeg");
  }


  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex);
    }
    return "";
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

  @PutMapping("/imagen/{id}")
  public ResponseEntity<String> actualizar(@PathVariable("id") Integer id,
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "file", required = false) MultipartFile file,
      @RequestBody Producto producto) {

    Optional<Producto> imagenExistente = productoService.listarPorId(id);
    if (!imagenExistente.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Producto imagenActual = imagenExistente.get();

    try {
      if (nombre != null) {
        imagenActual.setNombre(nombre);
        producto.setCategoria(producto.getCategoria());
      }

      if (file != null && !file.isEmpty()) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isValidImageExtension(fileExtension)) {
          return ResponseEntity.badRequest().body("Solo se permiten archivos con extensiones .png, .jpg y .jpeg");
        }

        String filename = UUID.randomUUID().toString();
        String newFileName = filename + fileExtension;
        Path filePath = Path.of(UPLOAD_DIR, newFileName);

        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Elimina el archivo de imagen existente solo si se ha copiado el archivo
        // adjunto con un nuevo nombre
        deleteImageFile(imagenActual.getImagen());

        String fileUrl = baseUri + "/imagenes/" + newFileName;
        imagenActual.setImagen(fileUrl);
      }

      productoService.actualizar(imagenActual);

      return ResponseEntity.ok(imagenActual.getImagen());
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private void deleteImageFile(String imageUrl) {
    // Extrae el nombre de archivo de la URL
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

    // Construye la ruta completa del archivo
    String filePath = UPLOAD_DIR + "/" + fileName;

    // Elimina el archivo de imagen
    try {
      Files.deleteIfExists(Paths.get(filePath));
    } catch (IOException e) {
      // Maneja cualquier error de eliminación del archivo
      e.printStackTrace();
    }
  }

  @DeleteMapping("/{id}")
  public void eliminar(@PathVariable(required = true) Integer id) {
    productoService.eliminarPorId(id);
  }
}