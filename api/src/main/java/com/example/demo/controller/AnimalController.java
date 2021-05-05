package com.example.demo.controller;

import com.example.demo.entity.Animal;
import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.service.AnimalService;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnimalController {

    @Autowired
    private AnimalService service;

    @Autowired
    private ClienteService serviceCliente;

    @PostMapping("/addAnimal/{email}")
    public Animal addAnimal(@PathVariable String email, @RequestBody Animal animal){
        serviceCliente.getClienteByEmail(email).setAnimal(animal);
        Cliente x1 = serviceCliente.getClienteByEmail(email);
        System.out.println(x1);
        return service.saveAnimal(animal);
    }

    @GetMapping("/animal/{id}")
    public Animal findAnimalById(@PathVariable int id){
        return service.getAnimalById(id);
    }
}
