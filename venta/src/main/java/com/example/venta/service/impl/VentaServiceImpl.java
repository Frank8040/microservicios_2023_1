package com.example.venta.service.impl;

import com.example.venta.entity.Venta;
import com.example.venta.repository.VentaRepository;
import com.example.venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta Venta) {
        return ventaRepository.save(Venta);
    }

    @Override
    public Venta actualizar(Venta Venta) {
        return ventaRepository.save(Venta);
    }

    @Override
    public Optional<Venta> listarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventaRepository.deleteById(id);
    }
}
