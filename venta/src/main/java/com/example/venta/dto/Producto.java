package com.example.venta.dto;

import lombok.Data;

@Data
public class Producto {
    private Integer id;
    private String nombre;
    private String imagen;
    private Categoria categoria;
}
