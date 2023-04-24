package com.example.crudspringbootventas.repository;

import com.example.crudspringbootventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    
}
