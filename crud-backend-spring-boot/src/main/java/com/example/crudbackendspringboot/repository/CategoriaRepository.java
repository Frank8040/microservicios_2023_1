package com.example.crudbackendspringboot.repository;

import com.example.crudbackendspringboot.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}