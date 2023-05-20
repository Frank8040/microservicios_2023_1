package com.example.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.catalogo.entity.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {

}
