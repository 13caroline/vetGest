package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:7777")
public class ClinicaController {
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private IntervencaoService intervencaoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping(path = "/clinica/adicionarClinica")
    public ResponseEntity<?> addClinica(@RequestBody Clinica clinica){
        Clinica existingClinica = clinicaService.getClinicaByEmail(clinica.getEmail());
        if(existingClinica!=null){
            return ResponseEntity.badRequest().body("Utilizador Clinica já se encontra registado");
        }
        clinicaService.saveClinica(clinica);
        return  ResponseEntity.accepted().body("Utilizador Clínica Registado com sucesso");
    }

    @GetMapping("/clinica/consultas")
    public ResponseEntity<?> getConsultas(){
        List<Intervencao> intervencoes = intervencaoService.getAllConsultas();
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Intervenções Agendadas!");
        }
        return  ResponseEntity.accepted().body(intervencoes);
    }

    @GetMapping("/clinica/clientes")
    public ResponseEntity<?> getUtentes(){
        List<Cliente> clientes = clienteService.getClientes();
        if(clientes.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Utentes Registados!");
        }
        return  ResponseEntity.accepted().body(clientes);
    }

    @PostMapping("/clinica/utentes/registar")
    public ResponseEntity<?> registarAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Cliente cliente = mapper.convertValue(node.get("cliente"),Cliente.class);
        Animal animal = mapper.convertValue(node.get("animal"),Animal.class);
        animalService.saveAnimal(animal);
        clienteService.getClienteByEmail(cliente.getEmail()).setAnimal(animal);
        if(animal==null || cliente==null){
            return ResponseEntity.badRequest().body("Erro a registar animal, por favor verifique os campos!");
        }
        return ResponseEntity.accepted().body("Animal registado com sucesso");
    }

    @PostMapping(path = "/clinica/clientes/registar")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente existingclient = clienteService.getClienteByEmail(cliente.getEmail());
        if(existingclient!=null){
            return ResponseEntity.badRequest().body("Email já se encontra registado");
        }
        clienteService.saveCliente(cliente);
        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    @GetMapping("/clinica/medicos")
    public ResponseEntity<?> getMedicos(){
        List<Veterinario> veterinarios = veterinarioService.getAllVeterinarios();
        if(veterinarios.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Veterinários");
        }
        return  ResponseEntity.accepted().body(veterinarios);
    }

    @PostMapping(path = "/clinica/medicos/registar")
    public ResponseEntity<String> addVeterinario(@RequestBody Veterinario veterinario){
        Veterinario existingVeterinario = veterinarioService.getVetByEmail(veterinario.getEmail());
        if(existingVeterinario!=null){
            return ResponseEntity.badRequest().body("Veterinário já se encontra registado");
        }
        veterinarioService.saveVeterinario(veterinario);
        return  ResponseEntity.accepted().body("Veterinário Registado com sucesso");
    }
}