package com.example.demo.controller;

import com.example.demo.entity.Utilizador;
import com.example.demo.service.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilizadorController {

    @Autowired
    private UtilizadorService service;

    @PostMapping("/addUtilizador")
    public Utilizador addUtilizador(@RequestBody Utilizador utilizador){
        return service.saveUtilizador(utilizador);
    }

    @GetMapping("/utilizador/{email}")
    public Utilizador findAnimalByEmail(@PathVariable String email){
        return service.getUtilizadorByEmail(email);
    }
}
