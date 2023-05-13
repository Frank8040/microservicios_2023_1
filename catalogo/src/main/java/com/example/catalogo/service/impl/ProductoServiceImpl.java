package com.example.catalogo.service.impl;

import com.example.catalogo.dto.Imagen;
import com.example.catalogo.entity.Categoria;
import com.example.catalogo.entity.Producto;
import com.example.catalogo.feign.ImagenFeign;
import com.example.catalogo.repository.CategoriaRepository;
import com.example.catalogo.repository.ProductoRepository;
import com.example.catalogo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImagenFeign imagenFeign;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getCategoriaId())
                .orElse(null);
        if (categoria == null) {
            categoria = categoriaRepository.save(producto.getCategoria());
        }
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getCategoriaId())
                .orElse(null);
        if (categoria == null) {
            categoria = categoriaRepository.save(producto.getCategoria());
        }
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> listarPorId(Integer id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            Imagen imagen = imagenFeign.listById(producto.getImagenId()).getBody();
            producto.setImagen(imagen);
        }
        return productoOptional;
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }
}
