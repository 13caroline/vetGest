package com.example.demo.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

