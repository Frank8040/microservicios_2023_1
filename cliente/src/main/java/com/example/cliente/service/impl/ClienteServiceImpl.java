package com.example.cliente.service.impl;

import com.example.cliente.entity.Cliente;
import com.example.cliente.repository.ClienteRepository;
import com.example.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente guardar(Cliente Cliente) {
        return clienteRepository.save(Cliente);
    }

    @Override
    public Cliente actualizar(Cliente Cliente) {
        return clienteRepository.save(Cliente);
    }

    @Override
    public Optional<Cliente> listarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        clienteRepository.deleteById(id);
    }
}