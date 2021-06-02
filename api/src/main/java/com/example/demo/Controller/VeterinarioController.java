package com.example.demo.Controller;

import com.example.demo.Entity.Veterinario;
import com.example.demo.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:7777")
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

