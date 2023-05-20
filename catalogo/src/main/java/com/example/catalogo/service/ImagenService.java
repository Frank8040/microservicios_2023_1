package com.example.catalogo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.catalogo.entity.Imagen;
import com.example.catalogo.repository.ImagenRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImagenService {
    @Autowired
    private ImagenRepository uploadRepository;

    public void UploadService(ImagenRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    public List<Imagen> getAllUploads() {
        return uploadRepository.findAll();
    }

    public Optional<Imagen> getUploadById(Long id) {
        return uploadRepository.findById(id);
    }

    public Imagen createUpload(MultipartFile file) throws IOException {
        Imagen upload = new Imagen();
        upload.setImageUrl(saveImage(file));
        return uploadRepository.save(upload);
    }

    public Imagen updateUpload(Long id, MultipartFile file) throws IOException {
        Optional<Imagen> existingUpload = uploadRepository.findById(id);

        if (existingUpload.isPresent()) {
            Imagen upload = existingUpload.get();
            upload.setImageUrl(saveImage(file));
            return uploadRepository.save(upload);
        } else {
            throw new IllegalArgumentException("No se encontró ninguna subida de imagen con el ID proporcionado");
        }
    }

    public void deleteUpload(Long id) {
        uploadRepository.deleteById(id);
    }

    private static final String UPLOAD_DIRECTORY = "src/main/resources/picture";

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = generateFileName(file.getOriginalFilename());
        saveImageToLocalFileSystem(file, fileName);
        return generateLocalImageUrl(fileName);
    }

    private void saveImageToLocalFileSystem(MultipartFile file, String fileName) throws IOException {
        Path filePath = Path.of(UPLOAD_DIRECTORY, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private String generateLocalImageUrl(String fileName) {
        return "/picture/" + fileName;
    }

    private String generateFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName + extension;
    }
}
