package com.example.demo.controller;

import com.example.demo.entity.Veterinario;
import com.example.demo.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VeterinarioController {
    @Autowired
    private VeterinarioService service;

    @PostMapping("/addVet")
    public Veterinario addVet(@RequestBody Veterinario vet){
        return service.saveVeterinario(vet);
    }

    @GetMapping("/vet")
    public Veterinario findVetByEmail(@RequestBody String email){
        return service.getVetByEmail(email);
    }
}

