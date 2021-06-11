package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.parser.Authorization;
import org.hibernate.dialect.SybaseAnywhereDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private  JwtUtil jwtUtil;

    //Check
    @CrossOrigin
    @PostMapping(path = "/cliente/registar")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente existingclient = clienteService.getClienteByEmail(cliente.getEmail());
        if(existingclient!=null){
            return ResponseEntity.badRequest().body("Email já se encontra registado");
        }
        clienteService.saveCliente(cliente);
        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    //ACABAR de testar se for para mandar as imunizaçoes tb
    @CrossOrigin
    @PostMapping("/cliente")
    public ResponseEntity<?> findClienteByEmail(@RequestBody Cliente email){
        Cliente cliente = clienteService.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
        List<Intervencao> intervencoes = new ArrayList<>();
        List<Animal> animais = cliente.getAnimais();
        System.out.println(animais);
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

    @CrossOrigin
    @PostMapping("/cliente/animais")
    public ResponseEntity<?> getClienteAnimais(@RequestBody Cliente email){
        Cliente cliente = clienteService.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
        return ResponseEntity.accepted().body(cliente);
    }

    @CrossOrigin
    @GetMapping("/cliente/medicos")
    public ResponseEntity<?> getVets(){
        List<Veterinario> vets = veterinarioService.getAllVeterinarios();
        return ResponseEntity.accepted().body(vets);
    }
    //Check
    @CrossOrigin
    @PostMapping("/cliente/animal/registar")
    public ResponseEntity<?> registarAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        System.out.println(node);
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

    //Check
    @CrossOrigin
    @PostMapping("/cliente/animal/{id_animal}")
    public ResponseEntity<?> getAnimal(@PathVariable int id_animal, @RequestBody Cliente emailCliente){
        Cliente cliente = clienteService.getClienteByEmail(emailCliente.getEmail());
        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal==null || !animais.contains(animal)){
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


    //Check
    @CrossOrigin
    @PostMapping("/cliente/editar/animal/{id_animal}")
    public ResponseEntity<?> editarAnimal(@PathVariable int id_animal,@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").get("email").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id_animal);
        if(animal==null || !animais.contains(animal)){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }
        Animal animalNew = mapper.convertValue(node.get("animal"),Animal.class);

        if(animalNew == null){
            return ResponseEntity.badRequest().body("Erro a editar animal, por favor verifique os campos!");
        }
        animal.setNome(animalNew.getNome());
        animal.setRaca(animalNew.getRaca());
        animal.setDataNascimento(animalNew.getDataNascimento());
        animal.setSexo(animalNew.getSexo());
        animal.setEspecie(animalNew.getEspecie());
        animal.setCor(animalNew.getCor());
        animal.setCauda(animalNew.getCauda());
        animal.setPelagem(animalNew.getPelagem());
        animal.setAltura(animalNew.getAltura());
        animal.setChip(animalNew.getChip());
        animal.setCastracao(animalNew.isCastracao());
        animal.setObservacoes(animalNew.getObservacoes());

        animalService.updateAnimal(animal);
        cliente.setAnimal(animal);

        return ResponseEntity.accepted().body("Animal editado com sucesso");
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/animal/{id_animal}/getvacinas")
    public ResponseEntity<?> getVacinas(@PathVariable int id_animal, @RequestBody Cliente emailCliente){
        Cliente cliente = clienteService.getClienteByEmail(emailCliente.getEmail());
        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal==null || !animais.contains(animal)){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }

        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(id_animal);
        //System.out.println(imunizacoes);
        return ResponseEntity.accepted().body(imunizacoes);
    }

    @CrossOrigin
    @PostMapping("/cliente/animal/intervencoes/{id}")
    public ResponseEntity<?> getIntervencoes(@PathVariable int id, @RequestBody Cliente emailCliente){
        Cliente cliente = clienteService.getClienteByEmail(emailCliente.getEmail());
        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id);

        if(animal==null || !animais.contains(animal)){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }

        List<Intervencao> intervencoes = intervencaoService.getIntervencoesAnimal(id);
        System.out.println(intervencoes);
        return ResponseEntity.accepted().body(intervencoes);
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/animal/{id_animal}/vacinas")
    public ResponseEntity<?> adicionarVacina(@PathVariable int id_animal,@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("email").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        if(cliente ==null){
            return ResponseEntity.badRequest().body("Erro a obter cliente!");
        }

        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal==null || !animais.contains(animal)){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }

        Imunizacao imunizacao = mapper.convertValue(node.get("imunizacao"),Imunizacao.class);

        if(imunizacao==null){
            return ResponseEntity.badRequest().body("Erro no agendamento de Imunização!");
        }

        imunizacao.setAnimal(animal);
        imunizacao.setTipo("Desparasitação");

        imunizacao.setVeterinario(null);
        //Estados
        //Atualizada
        //Administrada

        String data = imunizacao.getData();
        LocalDate date = LocalDate.parse(data);
        //System.out.println(date);

        if(date.isAfter(LocalDate.now()))
            imunizacao.setEstado("Atualizada");
        else
            imunizacao.setEstado("Administrada");

        if(imunizacao.getProxImunizacao()!=null){
            Imunizacao proximaImunizacao = new Imunizacao();
            proximaImunizacao.setData(imunizacao.getProxImunizacao());
            proximaImunizacao.setEstado("Atualizada");
            proximaImunizacao.setProxImunizacao(null);
            proximaImunizacao.setTipo("Desparasitação");
            proximaImunizacao.setVacina(imunizacao.getVacina());
            proximaImunizacao.setObservacoes(imunizacao.getObservacoes());
            proximaImunizacao.setTratamento(imunizacao.getTratamento());
            proximaImunizacao.setAnimal(imunizacao.getAnimal());
            proximaImunizacao.setVeterinario(imunizacao.getVeterinario());
            imunizacao.setProxImunizacao(null);
            imunizacaoService.saveImunizacao(imunizacao);
            imunizacaoService.saveImunizacao(proximaImunizacao);
            imunizacao.setProxima_imunizacao(proximaImunizacao);
            imunizacaoService.saveImunizacao(imunizacao);
        }
        else {
            imunizacao.setProxImunizacao(null);
            imunizacaoService.saveImunizacao(imunizacao);
        }
        return ResponseEntity.accepted().body("Imunização agendada!");

    }

    @CrossOrigin
    @PostMapping("/cliente/animal/confirma/imunizacao")
    public ResponseEntity<?> confirmaDesparasitacao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_imunizacao = node.get("id").asInt();
        Imunizacao imunizacao = imunizacaoService.getImunizacao(id_imunizacao);
        imunizacao.setData_toma(node.get("data").asText());
        imunizacao.setTratamento(node.get("tratamento").asText());
        if(node.get("dataProx").asText().equals("null"))
        {
            imunizacao.setProxImunizacao(null);
            System.out.println("\n\nAQUI1 : "+imunizacao.getProxImunizacao());
        }
        else{
            imunizacao.setProxImunizacao(node.get("dataProx").asText());
            System.out.println("\n\nAQUI 2: "+imunizacao.getProxImunizacao());
        }
        imunizacao.setEstado("Administrada");
        if(imunizacao.getProxImunizacao()!=null) {
            if (imunizacao.getProxima_imunizacao() == null) {
                Imunizacao proxima_Imunizacao = new Imunizacao();
                proxima_Imunizacao.setData(imunizacao.getProxImunizacao());
                proxima_Imunizacao.setEstado("Atualizada");
                proxima_Imunizacao.setTipo(imunizacao.getTipo());
                proxima_Imunizacao.setAnimal(imunizacao.getAnimal());
                proxima_Imunizacao.setVacina(imunizacao.getVacina());
                proxima_Imunizacao.setObservacoes(imunizacao.getObservacoes());
                proxima_Imunizacao.setTratamento(imunizacao.getTratamento());
                proxima_Imunizacao.setVeterinario(imunizacao.getVeterinario());
                imunizacaoService.saveImunizacao(proxima_Imunizacao);
                imunizacao.setProxima_imunizacao(proxima_Imunizacao);
            } else {
                Imunizacao proxima_Imunizacao = imunizacao.getProxima_imunizacao();
                proxima_Imunizacao.setProxImunizacao(node.get("dataProx").asText());
                imunizacaoService.saveImunizacao(proxima_Imunizacao);
            }
        }
        imunizacaoService.saveImunizacao(imunizacao);
        return ResponseEntity.accepted().body("Imunização confirmada com sucesso!");
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/consulta")
    public ResponseEntity<?> adicionarConsulta(@RequestBody String body) throws JsonProcessingException {
       ObjectMapper mapper = new ObjectMapper();
       JsonNode node = mapper.readTree(body);
       Intervencao intervencao = mapper.convertValue(node.get("intervencao"),Intervencao.class);
       //System.out.println(intervencao);
       int animal_id =mapper.convertValue(node.get("animal"),Integer.class);
       Animal animal = animalService.getAnimalById(animal_id);
       int vetId = node.get("veterinario").asInt();
       Veterinario vet = veterinarioService.getVetById(vetId);
       String clienteEmail = node.get("cliente").asText();
       Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

       if(intervencao==null || animal==null|| vet==null || cliente==null){
           return ResponseEntity.badRequest().body("Erro no agendamento de Consulta! Alguma das entidades nao existe!");
       }

       List<Animal> animais = cliente.getAnimais();

       if(!animais.contains(animal)){
           return ResponseEntity.badRequest().body("Erro a obter animal!");
       }

       intervencao.setAnimal(animal);
       intervencao.setVeterinario(vet);
       intervencao.setEstado("Pendente");
       intervencao.setTipo("Consulta");
       intervencao.setData_pedido(LocalDateTime.now().toString().substring(0,16));
       //System.out.println("\n\nAQUI:"+intervencao);
       intervencaoService.saveIntervencao(intervencao);
       return ResponseEntity.accepted().body("Consulta agendada!");
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/consultas")
    public ResponseEntity<?> getConsultas(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        List<Animal> animais = cliente.getAnimais();
        List<Intervencao> consultas= new ArrayList<>();
        animais.forEach(animal -> {
            List<Intervencao> consultas_temp = new ArrayList<>();
            consultas_temp = intervencaoService.getIntervencoesAnimal(animal.getId());
            //System.out.println(consultas_temp);
            consultas_temp.forEach(intervencao -> {
                if(intervencao.getTipo().equals("Consulta")){
                    consultas.add(intervencao);
                }
            });
        });
        if(consultas.size()==0){
            return ResponseEntity.accepted().body("Não tem consultas agendadas!");
        }
        return ResponseEntity.accepted().body(consultas);
    }

    @CrossOrigin
    @PostMapping("/cliente/animal/cirurgias")
    public ResponseEntity<?> getCirurgiasAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        List<Animal> animals = cliente.getAnimais();

        if(animal==null||!animals.contains(animal))
            return ResponseEntity.badRequest().body("Erro em alguma das Entidades");

        List<Intervencao> cirurgias = intervencaoService.findByAnimalIdAndTipo(id_animal,"Cirurgia");
        return ResponseEntity.accepted().body(cirurgias);
    }

    @CrossOrigin
    @PostMapping("/cliente/animal/consultas")
    public ResponseEntity<?> getConsultasAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        List<Animal> animals = cliente.getAnimais();

        if(animal==null||!animals.contains(animal))
            return ResponseEntity.badRequest().body("Erro em alguma das Entidades");

        List<Intervencao> consultas = intervencaoService.findByAnimalIdAndTipo(id_animal,"Consulta");
        return ResponseEntity.accepted().body(consultas);
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/cirurgias")
    public ResponseEntity<?> getCirurgias(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        List<Animal> animais = cliente.getAnimais();
        List<Intervencao> cirurgias= new ArrayList<>();
        animais.forEach(animal -> {
            List<Intervencao> cirurgias_temp = new ArrayList<>();
            cirurgias_temp = intervencaoService.getIntervencoesAnimal(animal.getId());
            ///System.out.println(cirurgias_temp);
            cirurgias_temp.forEach(intervencao -> {
                if(intervencao.getTipo().equals("Cirurgia")){
                    cirurgias.add(intervencao);
                }
            });
        });
        if(cirurgias.size()==0){
            return ResponseEntity.accepted().body("Não tem cirurgias agendadas!");
        }
        return ResponseEntity.accepted().body(cirurgias);
    }

    @CrossOrigin
    @PostMapping("/cliente/intervencoes")
    public ResponseEntity<?> getIntervencoes(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("cliente").asText();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        List<Animal> animais = cliente.getAnimais();
        List<Intervencao> intervencoes= new ArrayList<>();
        animais.forEach(animal -> {
            List<Intervencao> intervencoes_temp = new ArrayList<>();
            intervencoes_temp = intervencaoService.getIntervencoesAnimal(animal.getId());
            ///System.out.println(cirurgias_temp);
            intervencoes_temp.forEach(intervencao -> {
                if(intervencao.getTipo().equals("Cirurgia") || intervencao.getTipo().equals("Consulta")){
                    intervencoes.add(intervencao);
                }
            });
        });
        if(intervencoes.size()==0){
            return ResponseEntity.accepted().body("Não tem intervencoes agendadas!");
        }
        return ResponseEntity.accepted().body(intervencoes);
    }
    //Check
    @CrossOrigin
    @PostMapping("/cliente/animal/cancelar/{id_intervencao}")
    public ResponseEntity<?> cancelarIntervencao(@PathVariable int id_intervencao, @RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String clienteEmail = node.get("email").asText();
        int id_animal = node.get("animal").asInt();
        Cliente cliente = clienteService.getClienteByEmail(clienteEmail);

        if(cliente ==null) {
            return ResponseEntity.badRequest().body("Erro a obter cliente!");
        }
        List<Animal> animais = cliente.getAnimais();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal==null || !animais.contains(animal)){
            return ResponseEntity.badRequest().body("Erro a obter animal!");
        }

        List<Intervencao> consultas = intervencaoService.getIntervencoesAnimal(id_animal);
        Intervencao intervencao = intervencaoService.getIntervencao(id_intervencao);
        //System.out.println(intervencao);
        if(intervencao==null || !consultas.contains(intervencao) || intervencao.getEstado().equals("Cancelada")){
            return ResponseEntity.badRequest().body("Erro a cancelar intervenção!");
        }
        intervencao.setEstado("Cancelada");
        intervencaoService.saveIntervencao(intervencao);
        //System.out.println(intervencao);
        return ResponseEntity.accepted().body("Intervenção cancelada!");
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/getpreferencias")
    public ResponseEntity<?> getPreferencias(@RequestBody Cliente email){
        Cliente cliente = clienteService.getClienteByEmail(email.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
        return ResponseEntity.accepted().body(cliente);
    }

    //Check
    @CrossOrigin
    @PostMapping("/cliente/preferencias")
    public ResponseEntity<?> editarPreferencias(@RequestBody Cliente _cliente){
        Cliente cliente = clienteService.getClienteByEmail(_cliente.getEmail());
        if(cliente==null){
            return ResponseEntity.badRequest().body("Utilizador não existe!");
        }
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