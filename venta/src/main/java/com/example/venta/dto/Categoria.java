package com.example.venta.dto;

import lombok.Data;

@Data
public class Categoria {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Estado estado;

    public enum Estado {
        ACTIVO,
        INACTIVO
    }
}
