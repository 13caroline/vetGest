package com.example.demo.Intervencao;

import com.example.demo.Animal.Animal;
import com.example.demo.Animal.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntervencaoController {

    @Autowired
    private IntervencaoService service;
    @Autowired
    private AnimalService animalService;

    @PostMapping("/addIntervencao/{animalId}")
    public Intervencao addIntervencao(@PathVariable int animalId, @RequestBody Intervencao intervencao){
        //TODO ADICIONAR O VETERINÁRIO
        Animal animal = animalService.getAnimalById(animalId);
        Intervencao intervencao1 = new Intervencao(intervencao);
        //System.out.println(intervencao1 + "\n");
        intervencao1.setAnimal(animal);
        //System.out.println(animal + "\n");
        //System.out.println(intervencao1 + "\n");
        return service.saveIntervencao(intervencao1);
    }

}

