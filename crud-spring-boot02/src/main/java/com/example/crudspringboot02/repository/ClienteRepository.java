package com.example.crudspringboot02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudspringboot02.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}
