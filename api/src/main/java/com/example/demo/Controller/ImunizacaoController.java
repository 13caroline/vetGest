package com.example.demo.Controller;

import com.example.demo.Entity.Animal;
import com.example.demo.Service.AnimalService;
import com.example.demo.Entity.Imunizacao;
import com.example.demo.Service.ImunizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImunizacaoController {

    @Autowired
    private ImunizacaoService service;
    @Autowired
    private AnimalService animalService;

    @PostMapping("/addImunizacao/{animalId}")
    public Imunizacao addImunizacao(@PathVariable int animalId, @RequestBody Imunizacao imunizacao){
        //TODO ADICIONAR O VETERINÁRIO
        Animal animal = animalService.getAnimalById(animalId);
        Imunizacao imunizacao1 = new Imunizacao(imunizacao);
        //System.out.println(intervencao1 + "\n");
        imunizacao1.setAnimal(animal);
        //System.out.println(animal + "\n");
        //System.out.println(intervencao1 + "\n");
        return service.saveImunizacao(imunizacao1);
    }
}

