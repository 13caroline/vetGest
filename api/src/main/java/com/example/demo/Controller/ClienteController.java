package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



@RestController
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private IntervencaoService intervencaoService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ImunizacaoService imunizacaoService;
    @Autowired
    private VeterinarioService veterinarioService;


    @PostMapping(path = "/cliente/registar")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente existingclient = clienteService.getClienteByEmail(cliente.getEmail());
        if(existingclient!=null){
            return ResponseEntity.badRequest().body("Email já se encontra registado");
        }
        clienteService.saveCliente(cliente);
        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> findClienteByEmail(@RequestBody Cliente email){
        Cliente cliente = clienteService.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
        List<Intervencao> intervencoes = new ArrayList<>();
        List<Animal> animais = new ArrayList<>();
        animais= cliente.getAnimais();
        animais.forEach(animal -> {
            List<Intervencao> intervencoes_temp = new ArrayList<>();
            intervencoes_temp= intervencaoService.getIntervencoesAnimal(animal.getId());
            intervencoes_temp.forEach(intervencao -> {
                intervencoes.add(intervencao);
            });
        });
        ClienteIntervencoes clienteIntervencoes = new ClienteIntervencoes();
        clienteIntervencoes.setCliente(cliente);
        clienteIntervencoes.setIntervencoes(intervencoes);
        return ResponseEntity.accepted().body(clienteIntervencoes);
    }

    @PostMapping("/cliente/animal/registar")
    public ResponseEntity<?> registarAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        //System.out.println(node);
        String cliente = node.get("cliente").get("email").asText();
        //System.out.println(cliente);
        Animal animal = mapper.convertValue(node.get("animal"),Animal.class);
        //System.out.println(animal);
        animalService.saveAnimal(animal);
        Cliente c = clienteService.getClienteByEmail(cliente);
        //System.out.println(c);
        c.setAnimal(animal);
        //System.out.println(c);
        clienteService.updateCliente(c);
        if(animal==null || cliente==null){
            return ResponseEntity.badRequest().body("Erro a registar animal, por favor verifique os campos!");
        }
        return ResponseEntity.accepted().body("Animal registado com sucesso");
    }

    @GetMapping("/cliente/animal/{id_animal}")
    public ResponseEntity<?> getAnimal(@PathVariable int id_animal){
        Animal animal = animalService.getAnimalById(id_animal);
        if(animal==null){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesAnimal(id_animal);
        if(intervencoes==null){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }

        AnimalIntervencoes animalIntervencoes = new AnimalIntervencoes();
        animalIntervencoes.setAnimal(animal);
        animalIntervencoes.setIntervencoes(intervencoes);
        return ResponseEntity.accepted().body(animalIntervencoes);
    }

    @PutMapping("/cliente/animal/cancelar/{id_intervencao}")
    public ResponseEntity<?> cancelarIntervencao(@PathVariable int id_intervencao){
        Intervencao intervencao = intervencaoService.getIntervencao(id_intervencao);
        if(intervencao==null){
            return ResponseEntity.badRequest().body("Erro a cancelar intervenção!");
        }
        intervencao.setEstado("Cancelado");
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervenção cancelada!");
    }

    @PutMapping("/cliente/animal/{id_animal}")
    public ResponseEntity<?> editarAnimal(@PathVariable int id_animal,@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Cliente cliente = mapper.convertValue(node.get("cliente"),Cliente.class);
        Animal animal = mapper.convertValue(node.get("animal"),Animal.class);
        animal.setId(id_animal);
        animalService.saveAnimal(animal);
        clienteService.getClienteByEmail(cliente.getEmail()).setAnimal(animal);
        if(animal==null || cliente==null){
            return ResponseEntity.badRequest().body("Erro a editar animal, por favor verifique os campos!");
        }
        return ResponseEntity.accepted().body("Animal editado com sucesso");
    }

    @GetMapping("/cliente/animal/{id_animal}/vacinas")
    public ResponseEntity<?> getVacinas(@PathVariable int id_animal){
        List<Imunizacao> imunizacoes = new ArrayList<>();
        imunizacoes = imunizacaoService.getImunizacoes(id_animal);
        if(imunizacoes==null){
            return ResponseEntity.badRequest().body("Animal não tem nenhuma imunização realizada ou pendente!");
        }
        return ResponseEntity.accepted().body(imunizacoes);
    }

    @PostMapping("/cliente/animal/{id_animal}/vacinas")
    public ResponseEntity<?> adicionarVacina(@PathVariable int id_animal,@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Imunizacao imunizacao = mapper.convertValue(node.get("vacina"),Imunizacao.class);
        Animal animal= new Animal();
        animal=animalService.getAnimalById(id_animal);
        imunizacao.setAnimal(animal);
        int vet_id = mapper.convertValue(node.get("veterinario_id"),Integer.class);
        Veterinario veterinario= new Veterinario();
        veterinario = veterinarioService.getVetById(vet_id);
        imunizacao.setVeterinario(veterinario);
        imunizacao.setEstado("Pendente");
        System.out.println("\n\nAQUI:"+imunizacao);
        if(imunizacao==null || animal==null|| veterinario==null){
            return ResponseEntity.badRequest().body("Erro no agendamento de Imunização!");
        }
        imunizacaoService.saveImunizacao(imunizacao);
        return ResponseEntity.accepted().body("Imunização agendada!");
    }

   @PostMapping("/cliente/consulta")
    public ResponseEntity<?> adicionarConsulta(@RequestBody String body) throws JsonProcessingException {
       ObjectMapper mapper = new ObjectMapper();
       JsonNode node = mapper.readTree(body);
       Intervencao intervencao = mapper.convertValue(node.get("intervencao"),Intervencao.class);
       int animal_id =mapper.convertValue(node.get("animal"),Integer.class);
       int vet_id = mapper.convertValue(node.get("veterinario"),Integer.class);
       Animal animal = animalService.getAnimalById(animal_id);
       intervencao.setAnimal(animal);
       Veterinario veterinario = veterinarioService.getVetById(vet_id);
       intervencao.setVeterinario(veterinario);
       intervencao.setEstado("Pendente");
       intervencao.setTipo("Consulta");
       if(intervencao==null || animal==null|| veterinario==null){
           return ResponseEntity.badRequest().body("Erro no agendamento de Consulta!");
       }
       intervencaoService.saveIntervencao(intervencao);
       return ResponseEntity.accepted().body("Consulta agendada!");
    }

    @GetMapping("/cliente/consultas")
    public ResponseEntity<?> getConsultas(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal animal = new Animal();
        animal.setId(mapper.convertValue(node.get("animal"),Integer.class));
        System.out.println("\n\nAqui: "+animal);
        List<Intervencao> consultas_temp = new ArrayList<>();
        List<Intervencao> consultas= new ArrayList<>();
        consultas_temp = intervencaoService.getIntervencoesAnimal(animal.getId());
        consultas_temp.forEach(intervencao -> {
            if(intervencao.getTipo().equals("Consulta")){
                consultas.add(intervencao);
            }
        });
        if(consultas.size()==0){
            return ResponseEntity.badRequest().body("Não tem consultas agendadas!");
        }
        return ResponseEntity.accepted().body(consultas);
    }

    @GetMapping("/cliente/cirurgias")
    public ResponseEntity<?> getCirurgias(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal animal = new Animal();
        animal.setId(mapper.convertValue(node.get("animal"),Integer.class));
        System.out.println("\n\nAqui: "+animal);
        List<Intervencao> cirurgias_temp = new ArrayList<>();
        List<Intervencao> cirurgias= new ArrayList<>();
        cirurgias_temp = intervencaoService.getIntervencoesAnimal(animal.getId());
        cirurgias_temp.forEach(intervencao -> {
            if(intervencao.getTipo().equals("Cirurgia")){
                cirurgias.add(intervencao);
            }
        });
        if(cirurgias.size()==0){
            return ResponseEntity.badRequest().body("Não tem cirurgias agendadas!");
        }
        return ResponseEntity.accepted().body(cirurgias);
    }

    @GetMapping("/cliente/preferencias")
    public ResponseEntity<?> getPreferencias(@RequestBody Cliente email){
        Cliente cliente = clienteService.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
        return ResponseEntity.accepted().body(cliente);
    }

    @PostMapping("/cliente/preferencias")
    public ResponseEntity<?> editarPreferencias(@RequestBody Cliente _cliente){
        Cliente cliente = clienteService.getClienteByEmail(_cliente.getEmail());
        cliente.setNome(_cliente.getNome());
        cliente.setMorada(_cliente.getMorada());
        cliente.setConcelho(_cliente.getConcelho());
        cliente.setFreguesia(_cliente.getFreguesia());
        cliente.setContacto(_cliente.getContacto());
        try {
            clienteService.updateCliente(cliente);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro a alterar dados!");
        }
        return  ResponseEntity.accepted().body("Dados alterados com sucesso!");
    }
}