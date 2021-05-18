package com.example.demo.Service;

import com.example.demo.Entity.Cliente;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Cliente> getClientes(){
        return repository.findAll();
    }
}

