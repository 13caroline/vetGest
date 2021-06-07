package com.example.demo.Controller;


import com.example.demo.Entity.Animal;
import com.example.demo.Entity.Intervencao;
import com.example.demo.Entity.Veterinario;
import com.example.demo.Service.AnimalService;
import com.example.demo.Service.IntervencaoService;
import com.example.demo.Service.VeterinarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class VeterinarioController {
    @Autowired
    private VeterinarioService veterinarioService;
    @Autowired
    private IntervencaoService intervencaoService;
    @Autowired
    private AnimalService animalService;

    @CrossOrigin
    @PostMapping( "/medico/intervencoes")
    public ResponseEntity<?> getIntervencoes(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Veterinário não encontrado!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Veterinário não tem intervencões marcadas!");
        }
        return  ResponseEntity.accepted().body(intervencoes);
    }

    @CrossOrigin
    @PostMapping("/medico/agendar/intervencao")
    public ResponseEntity<?> addIntervencao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String email =node.get("email").asText();
        int animal_id = node.get("animal").asInt();
        Intervencao intervencao = mapper.convertValue(node.get("intervencao"), Intervencao.class);
        Veterinario veterinario = veterinarioService.getVetByEmail(email);
        if(veterinario==null ){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        Animal animal = animalService.getAnimalById(animal_id);
        if(animal==null ){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }
        intervencao.setAnimal(animal);
        intervencao.setVeterinario(veterinario);
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervenção agendada com sucesso!");
    }
}

