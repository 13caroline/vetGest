package com.example.demo.Controller;

import com.example.demo.Entity.Animal;
import com.example.demo.Entity.ClienteIntervencoes;
import com.example.demo.Entity.Intervencao;
import com.example.demo.Service.ClienteService;
import com.example.demo.Entity.Cliente;
import com.example.demo.Service.IntervencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



@RestController
public class ClienteController {
    @Autowired
    private ClienteService service;
    @Autowired
    private IntervencaoService intervencaoService;

    @PostMapping(path = "/cliente/registar")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente existingclient = new Cliente();
        existingclient= service.getClienteByEmail(cliente.getEmail());
        if(existingclient!=null){
            return ResponseEntity.badRequest().body("Email já se encontra registado");
        }
        service.saveCliente(cliente);
        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> findClienteByEmail(@RequestBody Cliente email){
        Cliente cliente = new Cliente();
        cliente=service.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe");
        }
        List<Intervencao> intervencoes = new ArrayList<>();
        List<Animal> animais = new ArrayList<>();
        animais= cliente.getAnimais();
        animais.forEach(animal -> {
            List<Intervencao> intervencoes_temp = new ArrayList<>();
            intervencoes_temp= intervencaoService.getIntervencoesAnimal(animal.getId());
            intervencoes_temp.forEach(intervencao -> {
                intervencoes.add(intervencao);
            });
        });
        ClienteIntervencoes clienteIntervencoes = new ClienteIntervencoes();
        clienteIntervencoes.setCliente(cliente);
        clienteIntervencoes.setIntervencoes(intervencoes);
        return ResponseEntity.accepted().body(clienteIntervencoes);
    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes(){
        return service.getClientes();
    }
}