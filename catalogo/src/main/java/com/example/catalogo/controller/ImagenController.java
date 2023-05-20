package com.example.catalogo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.catalogo.service.ImagenService;
import com.example.catalogo.entity.Imagen;

@RestController
@RequestMapping("/upload")
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

    @PostMapping
    public Imagen createUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadService.createUpload(file);
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
