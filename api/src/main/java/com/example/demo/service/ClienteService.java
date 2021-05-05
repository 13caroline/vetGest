package com.example.demo.service;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Utilizador;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente saveCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente getClienteByEmail(String email){
        return repository.findByEmail(email);
    }
}

