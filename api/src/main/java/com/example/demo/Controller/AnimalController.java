package com.example.demo.Controller;

import com.example.demo.Service.AnimalService;
import com.example.demo.Service.ClienteService;
import com.example.demo.Entity.Animal;
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
        return service.saveAnimal(animal);
    }

    @GetMapping("/animal/{id}")
    public Animal findAnimalById(@PathVariable int id){
        return service.getAnimalById(id);
    }
}
