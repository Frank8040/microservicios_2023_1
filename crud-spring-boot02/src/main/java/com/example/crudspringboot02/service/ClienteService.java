package com.example.crudspringboot02.service;
import com.example.crudspringboot02.entity.Cliente;
import java.util.List;
import java.util.Optional;
public interface ClienteService {
    public List<Cliente> listar();

    public Cliente guardar(Cliente Cliente);

    public Cliente actualizar(Cliente Cliente);

    public Optional<Cliente> listarPorId(Integer id);

    public void eliminarPorId(Integer id);
}
