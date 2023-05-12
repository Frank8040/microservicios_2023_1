package com.example.imagen.service.impl;

import com.example.imagen.entity.Imagen;
import com.example.imagen.repository.ImagenRepository;
import com.example.imagen.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenServiceImpl implements ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public List<Imagen> listar() {
        return imagenRepository.findAll();
    }

    @Override
    public Imagen guardar(Imagen cliente) {
        return imagenRepository.save(cliente);
    }

    @Override
    public Imagen actualizar(Imagen cliente) {
        return imagenRepository.save(cliente);
    }

    @Override
    public Optional<Imagen> listarPorId(Integer id) {
        return imagenRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        imagenRepository.deleteById(id);
    }
}