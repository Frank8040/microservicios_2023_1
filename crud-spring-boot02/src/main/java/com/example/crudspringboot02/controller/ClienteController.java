package com.example.crudspringboot02.controller;
import com.example.crudspringboot02.service.ClienteService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.crudspringboot02.entity.Cliente;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteService.listar());
    }

    @PostMapping()
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente Cliente) {
        return ResponseEntity.ok(clienteService.guardar(Cliente));
    }

    @PutMapping()
    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente Cliente) {
        return ResponseEntity.ok(clienteService.actualizar(Cliente));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(clienteService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable(required = true) Integer id) {
        clienteService.eliminarPorId(id);
        return "Eliminación Correcta";
    }
}
