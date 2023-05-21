package com.example.catalogo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.example.catalogo.service.ImagenService;
import com.example.catalogo.entity.Imagen;

@RestController
@RequestMapping("/imagen")
public class ImagenController {
    @Autowired
    private ImagenService uploadService;

    public void UploadController(ImagenService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping
    public List<Imagen> getAllUploads() {
        return uploadService.getAllUploads();
    }

    @GetMapping("/{id}")
    public Imagen getUploadById(@PathVariable Long id) {
        return uploadService.getUploadById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró ninguna subida de imagen con el ID proporcionado"));
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
public Imagen createUpload(@RequestParam(value = "file", required = false) MultipartFile file,
                           @RequestPart(value = "id") Long id,
                           @RequestPart(value = "imageUrl") String imageUrl) throws IOException {
    Imagen upload = new Imagen();

    if (file != null) {
        // Si se proporciona un archivo adjunto (form-data), procesarlo
        upload.setImageUrl(saveImage(file));
    } else if (imageUrl != null) {
        // Si se proporciona una URL de imagen en el cuerpo de la solicitud (raw JSON), establecerla en el objeto Imagen
        upload.setImageUrl(imageUrl);
    } else {
        // Manejar el caso en el que no se proporcione ni un archivo adjunto ni una URL de imagen
        throw new IllegalArgumentException("Debe proporcionar un archivo o una URL de imagen.");
    }

    // Si se proporciona un ID de imagen en el cuerpo de la solicitud (raw JSON), establecerlo en el objeto Imagen
    if (id != null) {
        upload.setId(id);
    }

    return uploadService.save(upload);
}

    private String saveImage(MultipartFile file) {
        return null;
    }

    @PutMapping("/{id}")
    public Imagen updateUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return uploadService.updateUpload(id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteUpload(@PathVariable Long id) {
        uploadService.deleteUpload(id);
    }
}
