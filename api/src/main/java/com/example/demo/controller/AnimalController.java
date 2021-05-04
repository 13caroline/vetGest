package com.example.demo.controller;

import com.example.demo.entity.Animal;
import com.example.demo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnimalController {

    @Autowired
    private AnimalService service;

    @PostMapping("/addAnimal")
    public Animal addAnimal(@RequestBody Animal animal){
        return service.saveAnimal(animal);
    }

    @GetMapping("/animal/{id}")
    public Animal findAnimalById(@PathVariable int id){
        return service.getAnimalById(id);
    }
}
