package com.example.demo.Controller;

import com.example.demo.Service.DicaService;
import com.example.demo.Entity.Dica;
import com.example.demo.Entity.Veterinario;
import com.example.demo.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DicaController {

    @Autowired
    private DicaService service;
    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping("/addDica/{emailVet}")
    public Dica addDica(@PathVariable String emailVet, @RequestBody Dica dica){
        Veterinario vet = veterinarioService.getVetByEmail(emailVet);
        Dica dica1 = new Dica(dica);
        dica1.setVeterinario(vet);
        //System.out.println(animal + "\n");
        //System.out.println(intervencao1 + "\n");
        return service.saveDica(dica1);
    }
}

