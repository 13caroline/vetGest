package com.example.demo.Animal;

import com.example.demo.Cliente.ClienteService;
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
