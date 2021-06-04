package com.example.demo.Controller;


import com.example.demo.Entity.Veterinario;
import com.example.demo.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medico")
public class VeterinarioController {
    @Autowired
    private VeterinarioService service;

    @CrossOrigin
    @PostMapping(path = "/intervencoes")
    public ResponseEntity<String> addCliente(@RequestBody String body){
        return  ResponseEntity.accepted().body("Sucesso");
    }

}

