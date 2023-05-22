package com.example.imagen.controller;

import com.example.imagen.service.ImagenService;
import com.example.imagen.entity.Imagen;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

  private final String UPLOAD_DIR = "src/main/resources/public/imagenes";

  @Autowired
  private ImagenService imagenService;

  @GetMapping()
  public ResponseEntity<List<Imagen>> list() {
    return ResponseEntity.ok().body(imagenService.listar());
  }

  @PostMapping()
  public ResponseEntity<String> save(@RequestBody Imagen imagen, @RequestParam("file") MultipartFile file) {

    if (file.isEmpty()) {
      System.err.println("No se ha proporcionado un archivo adjunto válido.");
      return ResponseEntity.badRequest().build();
    }

    try {
      String filename = UUID.randomUUID().toString();
      String fileExtension = getFileExtension(file.getOriginalFilename());
      String newFileName = filename + fileExtension;
      Path filePath = Path.of(UPLOAD_DIR, newFileName);

      String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      String fileUrl = baseUri + "/imagenes/" + newFileName;
      imagen.setUrl(fileUrl);

      imagenService.guardar(imagen);

      return ResponseEntity.ok(fileUrl);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private String getFileExtension(String filename) {
    int dotIndex = filename.lastIndexOf('.');
    return (dotIndex == -1) ? "" : filename.substring(dotIndex);
  }

  /*
   * private String getFileNameFromUrl(String url) {
   * int slashIndex = url.lastIndexOf('/');
   * return (slashIndex == -1) ? url : url.substring(slashIndex + 1);
   * }
   * 
   * private boolean isValidImageExtension(String filename) {
   * return filename.matches("^.*\\.(jpg|jpeg|png)$");
   * }
   * 
   * public String fileToBase64(String filePath) {
   * try {
   * File file = new File(filePath);
   * byte[] fileContent = Files.readAllBytes(file.toPath());
   * return Base64.getEncoder().encodeToString(fileContent);
   * } catch (IOException e) {
   * e.printStackTrace();
   * }
   * return null;
   * }
   */
  @PutMapping()
  public ResponseEntity<String> update(@RequestBody Imagen imagen, @RequestParam("file") MultipartFile file) {
    try {
      // Obtén la imagen existente antes de la actualización
      Optional<Imagen> imagenExistente = imagenService.listarPorId(imagen.getId());
      if (!imagenExistente.isPresent()) {
        return ResponseEntity.notFound().build();
      }
      Imagen imagenActual = imagenExistente.get();

      if (file.isEmpty()) {
        // No se proporcionó un archivo adjunto, conserva la URL y los datos existentes
        // de la imagen y actualiza otros campos
        imagen.setUrl(imagenActual.getUrl());
        imagenService.actualizar(imagen);
        return ResponseEntity.ok(imagen.getUrl());
      }

      // Se proporcionó un archivo adjunto, realiza la actualización de la imagen

      String filename = UUID.randomUUID().toString();
      String fileExtension = getFileExtension(file.getOriginalFilename());
      String newFileName = filename + fileExtension;
      Path filePath = Path.of(UPLOAD_DIR, newFileName);

      String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

      String fileUrl = baseUri + "/imagenes/" + newFileName;
      imagen.setUrl(fileUrl);

      // Elimina el archivo de imagen existente
      deleteImageFile(imagenActual.getUrl());

      // Actualiza los datos de la imagen con los valores actualizados y guarda la
      // imagen
      imagenActual.setType(imagen.getType());
      imagenActual.setUrl(imagen.getUrl());
      imagenService.actualizar(imagenActual);

      return ResponseEntity.ok(fileUrl);
    } catch (IOException e) {
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

  @GetMapping("/{id}")
  public ResponseEntity<Imagen> listById(@PathVariable(required = true) Integer id) {
    return ResponseEntity.ok().body(imagenService.listarPorId(id).get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteById(@PathVariable(required = true) Integer id) {
    // Obtener la imagen por su ID
    Optional<Imagen> imagenOptional = imagenService.listarPorId(id);
    if (!imagenOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    // Eliminar el archivo de la carpeta
    String filePath = imagenOptional.get().getUrl();
    try {
      Files.deleteIfExists(Paths.get(filePath));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // Eliminar el producto
    imagenService.eliminarPorId(id);

    return ResponseEntity.ok("Eliminación correcta");
  }
}
