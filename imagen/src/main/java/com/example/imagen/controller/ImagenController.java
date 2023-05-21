package com.example.imagen.controller;

import com.example.imagen.service.ImagenService;
import com.example.imagen.entity.Imagen;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/imagen")
public class ImagenController {
  @Autowired
  private ImagenService imagenService;

  @GetMapping()
  public ResponseEntity<List<Imagen>> list() {
    return ResponseEntity.ok().body(imagenService.listar());
  }

  @PostMapping()
  public ResponseEntity<String> save(@ModelAttribute Imagen imagen, @RequestParam("file") MultipartFile file) {
    // Verifica si se ha proporcionado un archivo
    if (file.isEmpty()) {
      System.err.println("No se ha proporcionado un archivo adjunto válido.");
      return ResponseEntity.badRequest().build();
    }

    try {
      // Verifica la extensión del archivo
      String originalFilename = file.getOriginalFilename();
      if (originalFilename == null) {
        System.err.println("No se puede obtener el nombre del archivo adjunto.");
        return ResponseEntity.badRequest().build();
      }

      if (!isValidImageExtension(originalFilename)) {
        System.err.println("La extensión del archivo adjunto no es compatible.");
        return ResponseEntity.badRequest().build();
      }

      // Resto del código para guardar el archivo y realizar otras operaciones
      String filename = UUID.randomUUID().toString();
      byte[] bytes = file.getBytes();
      long fileSize = file.getSize();
      long maxFileSize = 5 * 1024 * 1024;

      if (fileSize > maxFileSize) {
        return ResponseEntity.badRequest().body("File exceeds, max 5MB");
      }

      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String newFileName = filename + fileExtension;

      String uploadDir = "src/main/resources/imagenes";
      File folder = new File(uploadDir);
      if (!folder.exists()) {
        folder.mkdirs();
      }

      Path path = Paths.get(uploadDir + "/" + newFileName);
      Files.write(path, bytes);

      String base64File = fileToBase64(path.toString());

      // Asigna la URL del archivo a la imagen como una cadena de texto
      imagen.setUrl(path.toString());

      // Guarda o realiza otras operaciones necesarias con el objeto 'imagen'
      imagenService.guardar(imagen);

      return ResponseEntity.ok(base64File);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private boolean isValidImageExtension(String filename) {
    return filename.matches("^.*\\.(jpg|jpeg|png)$");
  }

  public String fileToBase64(String filePath) {
    try {
      File file = new File(filePath);
      byte[] fileContent = Files.readAllBytes(file.toPath());
      return Base64.getEncoder().encodeToString(fileContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @PutMapping()
  public ResponseEntity<String> update(@ModelAttribute Imagen imagen, @RequestParam("file") MultipartFile file) {
    // Verifica si se ha proporcionado un archivo
    if (file.isEmpty()) {
      System.err.println("No se ha proporcionado un archivo adjunto válido.");
      return ResponseEntity.badRequest().build();
    }

    try {
      // Verifica la extensión del archivo
      String originalFilename = file.getOriginalFilename();
      if (originalFilename == null) {
        System.err.println("No se puede obtener el nombre del archivo adjunto.");
        return ResponseEntity.badRequest().build();
      }

      if (!isValidImageExtension(originalFilename)) {
        System.err.println("La extensión del archivo adjunto no es compatible.");
        return ResponseEntity.badRequest().build();
      }

      // Resto del código para guardar el archivo y realizar otras operaciones
      String filename = UUID.randomUUID().toString();
      byte[] bytes = file.getBytes();
      long fileSize = file.getSize();
      long maxFileSize = 5 * 1024 * 1024;

      if (fileSize > maxFileSize) {
        return ResponseEntity.badRequest().body("File exceeds, max 5MB");
      }

      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String newFileName = filename + fileExtension;

      String uploadDir = "src/main/resources/imagenes";
      File folder = new File(uploadDir);
      if (!folder.exists()) {
        folder.mkdirs();
      }

      Path path = Paths.get(uploadDir + "/" + newFileName);
      Files.write(path, bytes);

      String base64File = fileToBase64(path.toString());

      // Asigna la URL del archivo a la imagen como una cadena de texto
      imagen.setUrl(path.toString());

      // Guarda o realiza otras operaciones necesarias con el objeto 'imagen'
      imagenService.actualizar(imagen);

      return ResponseEntity.ok(base64File);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
