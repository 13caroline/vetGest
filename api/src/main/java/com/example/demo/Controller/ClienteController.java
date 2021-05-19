package com.example.demo.Controller;

import com.example.demo.Service.ClienteService;
import com.example.demo.Entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ClienteController {
    @Autowired
    private ClienteService service;


    @PostMapping(path = "/addCliente")
    public Cliente addCliente(@RequestBody Cliente cliente){
        return service.saveCliente(cliente);
    }

    @GetMapping("/cliente/{email}")
    public Cliente findClienteByEmail(@RequestBody String email){
        return service.getClienteByEmail(email);
    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes(){
        return service.getClientes();
    }
}