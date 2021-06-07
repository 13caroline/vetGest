package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
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
    @Autowired
    private ImunizacaoService imunizacaoService;

    @CrossOrigin
    @PostMapping(path = "/clinica/adicionarClinica")
    public ResponseEntity<?> addClinica(@RequestBody Clinica clinica){
        Clinica existingClinica = clinicaService.getClinicaByEmail(clinica.getEmail());
        if(existingClinica!=null){
            return ResponseEntity.badRequest().body("Utilizador Clinica já se encontra registado");
        }
        clinicaService.saveClinica(clinica);
        return  ResponseEntity.accepted().body("Utilizador registado com Sucesso");
    }

    @CrossOrigin
    @GetMapping("/clinica/consultas")
    public ResponseEntity<?> getConsultas(){
        List<Intervencao> intervencoes = intervencaoService.getAllConsultas();
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Intervenções Agendadas!");
        }
        return  ResponseEntity.accepted().body(intervencoes);
    }

    @CrossOrigin
    @GetMapping("/clinica/clientes")
    public ResponseEntity<?> getClientes(){
        List<Cliente> clientes = clienteService.getClientes();
        if(clientes.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Clientes Registados!");
        }
        return  ResponseEntity.accepted().body(clientes);
    }

    @CrossOrigin
    @GetMapping("/clinica/utentes")
    public ResponseEntity<?> getUtentes(){
        List<Cliente> clientes = clienteService.getClientes();
        if(clientes.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Clientes Registados!");
        }
        JSONObject animais = new JSONObject();
        clientes.forEach(cliente -> {
            cliente.getAnimais().forEach(animal -> {
                try {
                    JSONObject utente= new JSONObject();
                    JSONObject a= new JSONObject();
                    a.put("id",animal.getId());
                    a.put("nome",animal.getNome());
                    a.put("raca",animal.getRaca());
                    a.put("dataNascimento",animal.getDataNascimento());
                    a.put("sexo",animal.getSexo());
                    a.put("especie",animal.getEspecie());
                    a.put("cor",animal.getCor());
                    a.put("cauda",animal.getCauda());
                    a.put("pelagem",animal.getPelagem());
                    a.put("altura",animal.getAltura());
                    a.put("chip",animal.getChip());
                    a.put("castracao",animal.isCastracao());
                    a.put("observacoes",animal.getObservacoes());
                    a.put("cliente_nome",cliente.getNome());
                    a.put("cliente_email",cliente.getEmail());
                    utente.put("animal",a);
                    animais.accumulate("utentes",utente);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });
        return  ResponseEntity.accepted().body(animais.toString());
    }

    @CrossOrigin
    @PostMapping("/clinica/utentes/registar")
    public ResponseEntity<?> registarAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String cliente = node.get("cliente").get("email").asText();
        Animal animal = mapper.convertValue(node.get("animal"),Animal.class);
        animalService.saveAnimal(animal);
        Cliente c = clienteService.getClienteByEmail(cliente);
        c.setAnimal(animal);
        clienteService.updateCliente(c);
        if(animal==null || cliente==null){
            return ResponseEntity.badRequest().body("Erro a registar animal, por favor verifique os campos!");
        }
        return ResponseEntity.accepted().body("Animal registado com sucesso");
    }

    @CrossOrigin
    @PostMapping(path = "/clinica/clientes/registar")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
        Cliente existingclient = clienteService.getClienteByEmail(cliente.getEmail());
        if(existingclient!=null){
            return ResponseEntity.badRequest().body("Email já se encontra registado");
        }
        clienteService.saveCliente(cliente);
        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    @CrossOrigin
    @GetMapping("/clinica/medicos")
    public ResponseEntity<?> getMedicos(){
        List<Veterinario> veterinarios = veterinarioService.getAllVeterinarios();
        if(veterinarios.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Veterinários");
        }
        return  ResponseEntity.accepted().body(veterinarios);
    }

    @CrossOrigin
    @PostMapping(path = "/clinica/medicos/registar")
    public ResponseEntity<String> addVeterinario(@RequestBody Veterinario veterinario){
        Veterinario existingVeterinario = veterinarioService.getVetByEmail(veterinario.getEmail());
        if(existingVeterinario!=null){
            return ResponseEntity.badRequest().body("Veterinário já se encontra registado");
        }
        veterinarioService.saveVeterinario(veterinario);
        return  ResponseEntity.accepted().body("Veterinário Registado com sucesso");
    }

    @CrossOrigin
    @PostMapping("/clinica/intervencao/agendar")
    public ResponseEntity<?> adicionarIntervencao(@RequestBody String body) throws JsonProcessingException {
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

        String data = intervencao.getData();
        String hora = intervencao.getHora();

        List<Intervencao> intervencoes = intervencaoService.findAllByVeterinarioIdAndEstadoEquals(vetId,"Agendada");
        List<Intervencao> temp = new ArrayList<>();
        intervencoes.forEach(intervencao1 -> {
            if (intervencao1.getData().equals(data) && intervencao1.getHora().equals(hora)) {
                temp.add(intervencao1);
            }
        });

        if(!temp.isEmpty()){
            return ResponseEntity.badRequest().body("Erro no agendamento de Consulta! Horario Indisponivel!");
        }

        intervencao.setAnimal(animal);
        intervencao.setVeterinario(vet);
        intervencao.setEstado("Agendada");
        intervencao.setData_pedido(LocalDateTime.now().toString());
        //System.out.println("\n\nAQUI:"+intervencao);
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervençao agendada!");
    }

    @CrossOrigin
    @PutMapping("/clinica/intervencao/cancelar")
    public ResponseEntity<?> cancelarIntervencao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);

        int id_intervencao = node.get("id").asInt();
        Intervencao intervencao = intervencaoService.getIntervencao(id_intervencao);

        if(intervencao==null){
            return ResponseEntity.badRequest().body("Erro a cancelar intervenção! Intervençao nao existe!");
        }

        intervencao.setEstado("Cancelado");
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervenção cancelada!");
    }

    @CrossOrigin
    @PutMapping("/clinica/utente/editar")
    public ResponseEntity<?> editarAnimal(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("id").asInt();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal == null){
            return ResponseEntity.badRequest().body("Animal nao existe");
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

        return ResponseEntity.accepted().body("Animal editado com sucesso");
    }
}