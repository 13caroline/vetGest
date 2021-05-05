package com.example.demo.Utilizador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilizadorController {

    @Qualifier("utilizadorService")
    @Autowired
    private UtilizadorService service;

    @PostMapping(path = "/addUtilizador")
    public Utilizador addUtilizador(@RequestBody Utilizador utilizador){
        return service.saveUtilizador(utilizador);
    }

    @GetMapping("/utilizador/{email}")
    public Utilizador findAnimalByEmail(@PathVariable String email){
        return service.getUtilizadorByEmail(email);
    }
}
