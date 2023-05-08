package com.example.venta.dto;

import lombok.Data;

@Data
public class Cliente {
    private Integer id;
    private String nombre;
    private String dni;
    private String apellidoPaterno;
    private String apellidoMaterno;
}
