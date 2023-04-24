package com.example.venta.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VentaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double cantidad;
    private Double precio;
    private Integer productoId;

    public VentaDetalle() {
        this.cantidad = (double) 0;
        this.precio = (double) 0;
    }
}
