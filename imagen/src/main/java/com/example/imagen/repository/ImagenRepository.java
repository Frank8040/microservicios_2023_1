package com.example.imagen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.imagen.entity.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    
}
