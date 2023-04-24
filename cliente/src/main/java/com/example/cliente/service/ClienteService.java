package com.example.cliente.service;
import com.example.cliente.entity.Cliente;
import java.util.List;
import java.util.Optional;
public interface ClienteService {
    public List<Cliente> listar();

    public Cliente guardar(Cliente Cliente);

    public Cliente actualizar(Cliente Cliente);

    public Optional<Cliente> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
