package com.example.demo.Service;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.Entity.Animal;
import com.example.demo.Entity.Cliente;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepository repository;

    public Cliente saveCliente(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return repository.save(cliente);
    }

    public Cliente getClienteByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    public Cliente updateCliente(Cliente cliente){
        return repository.save(cliente);
    }

}

