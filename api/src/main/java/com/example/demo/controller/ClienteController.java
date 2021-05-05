package com.example.demo.controller;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClienteController {
    @Autowired
    private ClienteService service;

    @PostMapping(path = "/addCliente")
    public Cliente addCliente(@RequestBody Cliente cliente){
        return service.saveCliente(cliente);
    }

    @GetMapping("/cliente")
    public Cliente findAnimalByEmail(@RequestBody String email){
        return service.getClienteByEmail(email);
    }
}