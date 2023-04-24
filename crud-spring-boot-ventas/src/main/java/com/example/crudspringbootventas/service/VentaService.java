package com.example.crudspringbootventas.service;

import com.example.crudspringbootventas.entity.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    public List<Venta> listar();

    public Venta guardar(Venta Cliente);

    public Venta actualizar(Venta Cliente);

    public Optional<Venta> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
