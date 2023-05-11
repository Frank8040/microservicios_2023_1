package com.example.cliente.controller;

import com.example.cliente.service.ClienteService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cliente.entity.Cliente;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<Cliente>> list() {
        return ResponseEntity.ok().body(clienteService.listar());
    }

    @PostMapping()
    public ResponseEntity<Cliente> save(@RequestBody Cliente Cliente) {
        return ResponseEntity.ok(clienteService.guardar(Cliente));
    }

    @PutMapping()
    public ResponseEntity<Cliente> update(@RequestBody Cliente Cliente) {
        return ResponseEntity.ok(clienteService.actualizar(Cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(clienteService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(required = true) Integer id) {
        clienteService.eliminarPorId(id);
        return ResponseEntity.ok("Eliminación Correcta");
    }

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/{id}")
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }
}
