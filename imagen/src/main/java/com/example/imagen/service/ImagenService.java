package com.example.imagen.service;

import com.example.imagen.entity.Imagen;
import java.util.List;
import java.util.Optional;

public interface ImagenService {
    public List<Imagen> listar();

    public Imagen guardar(Imagen imagen);

    public Imagen actualizar(Imagen imagen);

    public Optional<Imagen> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
