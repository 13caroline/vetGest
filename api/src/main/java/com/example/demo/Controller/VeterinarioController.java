package com.example.demo.Controller;

import com.example.demo.Entity.Veterinario;
import com.example.demo.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class VeterinarioController {
    @Autowired
    private VeterinarioService service;

    @CrossOrigin
    @PostMapping("/addVet")
    public Veterinario addVet(@RequestBody Veterinario vet){
        return service.saveVeterinario(vet);
    }

    @CrossOrigin
    @GetMapping("/vet")
    public Veterinario findVetByEmail(@RequestBody String email){
        return service.getVetByEmail(email);
    }
}

