package com.example.demo.Controller;


import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class VeterinarioController {
    @Autowired
    private VeterinarioService veterinarioService;
    @Autowired
    private IntervencaoService intervencaoService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ImunizacaoService imunizacaoService;

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

    @CrossOrigin
    @PostMapping("/medico/utentes")
    public ResponseEntity<?> getUtentes(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null ){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Veterinário não tem intervencões marcadas!");
        }
        List<Animal> animais = new ArrayList<>();
        intervencoes.forEach(intervencao -> {
            animais.add(intervencao.getAnimal());
        });
        return ResponseEntity.accepted().body(animais);
    }

    @CrossOrigin
    @PostMapping("/medico/consultas")
    public ResponseEntity<?> getConsultas(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null ){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Veterinário não tem consultas marcadas!");
        }
        List<Intervencao> consultas = new ArrayList<>();
        intervencoes.forEach(intervencao -> {
            if(intervencao.getTipo().equals("Consulta")){
                consultas.add(intervencao);
            }
        });

        return ResponseEntity.accepted().body(consultas);
    }

    @CrossOrigin
    @PostMapping("/medico/cirurgias")
    public ResponseEntity<?> getCirurgias(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null ){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Veterinário não tem cirurgias marcadas!");
        }
        List<Intervencao> consultas = new ArrayList<>();
        intervencoes.forEach(intervencao -> {
            if(intervencao.getTipo().equals("Cirurgia")){
                consultas.add(intervencao);
            }
        });
        return ResponseEntity.accepted().body(consultas);
    }

    @CrossOrigin
    @PostMapping("/medico/utente")
    public ResponseEntity<?> getUtente(@RequestBody Animal id) throws JSONException {
        Animal a = animalService.getAnimalById(id.getId());
        if(a==null ){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }
        JSONObject animal = new JSONObject();
        animal.put("animal",a);
        return ResponseEntity.accepted().body(animal);
    }


    @CrossOrigin
    @PostMapping("/medico/animal/adiciona/imunizacao")
    public ResponseEntity<?> adicionarVacina(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal animal = animalService.getAnimalById(node.get("animal").asInt());
        if(animal==null){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }
        Imunizacao imunizacao =  mapper.convertValue(node.get("imunizacao"),Imunizacao.class);
        if(imunizacao==null){
            return ResponseEntity.badRequest().body("Erro no agendamento de Imunização!");
        }
        Veterinario veterinario = veterinarioService.getVetByEmail(node.get("veterinario").asText());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        imunizacao.setAnimal(animal);
        imunizacao.setVeterinario(veterinario);
        imunizacao.setEstado("Agendada");
        if(imunizacao.getProxImunizacao()!=null){
            Imunizacao proximaImunizacao = new Imunizacao();
            proximaImunizacao.setData(imunizacao.getProxImunizacao());
            proximaImunizacao.setEstado("Agendada");
            proximaImunizacao.setProxImunizacao(null);
            proximaImunizacao.setTipo(imunizacao.getTipo());
            proximaImunizacao.setVacina(imunizacao.getVacina());
            proximaImunizacao.setObservacoes(imunizacao.getObservacoes());
            proximaImunizacao.setTratamento(imunizacao.getTratamento());
            proximaImunizacao.setAnimal(imunizacao.getAnimal());
            proximaImunizacao.setVeterinario(imunizacao.getVeterinario());
            imunizacaoService.saveImunizacao(proximaImunizacao);
            imunizacao.setProxima_imunizacao(proximaImunizacao);
        }
        imunizacaoService.saveImunizacao(imunizacao);
        return ResponseEntity.accepted().body("Imunização agendada!");
    }

    @CrossOrigin
    @PostMapping("/medico/utente/vacinas")
    public ResponseEntity<?> getVacinas(@RequestBody Animal id){
        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(id.getId());
        if(imunizacoes.size()==0)
        {
            return ResponseEntity.badRequest().body("Não tem vacinas agendadas!");
        }
        List<Imunizacao> vacinas = new ArrayList<>();
        imunizacoes.forEach(imunizacao -> {
            if(imunizacao.getTipo().equals("Vacina"))
            {
                vacinas.add(imunizacao);
            }
        });
        return ResponseEntity.accepted().body(vacinas);
    }

    @CrossOrigin
    @PostMapping("/medico/utente/desparasitacoes")
    public ResponseEntity<?> getDesparasitacoes(@RequestBody Animal id){
        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(id.getId());
        if(imunizacoes.size()==0)
        {
            return ResponseEntity.badRequest().body("Não tem desparasitações agendadas!");
        }
        List<Imunizacao> desparasitacoes = new ArrayList<>();
        imunizacoes.forEach(imunizacao -> {
            if(imunizacao.getTipo().equals("Desparasitação"))
            {
                desparasitacoes.add(imunizacao);
            }
        });
        return ResponseEntity.accepted().body(desparasitacoes);
    }

    @CrossOrigin
    @PostMapping("/medico/animal/confirma/imunizacao")
    public ResponseEntity<?> confirmaDesparasitacao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_imunizacao = node.get("id").asInt();
        Imunizacao imunizacao = imunizacaoService.getImunizacao(id_imunizacao);
        imunizacao.setData(node.get("data").asText());
        imunizacao.setTratamento(node.get("tratamento").asText());
        imunizacao.setProxImunizacao(node.get("dataProx").asText());
        imunizacao.setEstado("Administrada");
        if(imunizacao.getProxima_imunizacao()==null){
            Imunizacao proxima_Imunizacao = new Imunizacao();
            proxima_Imunizacao.setData(imunizacao.getProxImunizacao());
            proxima_Imunizacao.setEstado("Agendada");
            proxima_Imunizacao.setTipo(imunizacao.getTipo());
            proxima_Imunizacao.setAnimal(imunizacao.getAnimal());
            proxima_Imunizacao.setVacina(imunizacao.getVacina());
            proxima_Imunizacao.setObservacoes(imunizacao.getObservacoes());
            proxima_Imunizacao.setTratamento(imunizacao.getTratamento());
            proxima_Imunizacao.setVeterinario(imunizacao.getVeterinario());
            imunizacaoService.saveImunizacao(proxima_Imunizacao);
            imunizacao.setProxima_imunizacao(proxima_Imunizacao);
        }
        else{
            Imunizacao proxima_Imunizacao = imunizacao.getProxima_imunizacao();
            proxima_Imunizacao.setProxImunizacao(node.get("dataProx").asText());
            imunizacaoService.saveImunizacao(proxima_Imunizacao);
        }
        imunizacaoService.saveImunizacao(imunizacao);
        return ResponseEntity.accepted().body("Imunização confirmada com sucesso!");
    }
}

