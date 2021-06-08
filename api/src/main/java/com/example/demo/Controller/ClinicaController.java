package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
        JSONObject intervencoesObject = new JSONObject();
        intervencoes.forEach(intervencao -> {
            Cliente cliente = clienteService.findClienteByAnimais(intervencao.getAnimal());
            JSONObject c = new JSONObject();
            JSONObject a = new JSONObject();
            JSONObject i = new JSONObject();
            try {
                c.put("id",cliente.getId());
                c.put("nome",cliente.getNome());
                c.put("concelho",cliente.getConcelho());
                c.put("contacto",cliente.getContacto());
                c.put("freguesia",cliente.getFreguesia());
                c.put("morada",cliente.getMorada());
                c.put("nif",cliente.getNif());
                a.put("id",intervencao.getAnimal().getId());
                a.put("nome",intervencao.getAnimal().getNome());
                a.put("raca",intervencao.getAnimal().getRaca());
                a.put("dataNascimento",intervencao.getAnimal().getDataNascimento());
                a.put("sexo",intervencao.getAnimal().getSexo());
                a.put("especie",intervencao.getAnimal().getEspecie());
                a.put("cor",intervencao.getAnimal().getCor());
                a.put("cauda",intervencao.getAnimal().getCauda());
                a.put("pelagem",intervencao.getAnimal().getPelagem());
                a.put("altura",intervencao.getAnimal().getAltura());
                a.put("chip",intervencao.getAnimal().getChip());
                a.put("castracao",intervencao.getAnimal().isCastracao());
                a.put("observacoes",intervencao.getAnimal().getObservacoes());
                a.put("cliente_nome",cliente.getNome());
                a.put("cliente_email",cliente.getEmail());
                i.put("id",intervencao.getId());
                i.put("data",intervencao.getData());
                i.put("observacoes",intervencao.getObservacoes());
                i.put("hora",intervencao.getHora());
                i.put("descricao",intervencao.getDescricao());
                i.put("estado",intervencao.getEstado());
                i.put("motivo",intervencao.getMotivo());
                i.put("tipo",intervencao.getTipo());
                i.put("data_pedido",intervencao.getData_pedido());
                i.put("veterinario_id",intervencao.getVeterinario().getId());
                i.put("veterinario_nome",intervencao.getVeterinario().getNome());
                i.put("cliente",c);
                i.put("animal",a);
                intervencoesObject.accumulate("intervencoes",i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return  ResponseEntity.accepted().body(intervencoesObject.toString());
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
    @PostMapping("/clinica/utente")
    public ResponseEntity<?> getUtente(@RequestBody String body) throws JSONException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal a = animalService.getAnimalById(node.get("id").asInt());
        if(a==null){
            return ResponseEntity.badRequest().body("Utente não encontrado!");
        }
        Cliente c = clienteService.findClienteByAnimais(a);
        if(c==null){
            return ResponseEntity.badRequest().body("Cliente não encontrado!");
        }
        JSONObject animal = new JSONObject();
        animal.put("id",a.getId());
        animal.put("nome",a.getNome());
        animal.put("raca",a.getRaca());
        animal.put("dataNascimento",a.getDataNascimento());
        animal.put("sexo",a.getSexo());
        animal.put("especie",a.getEspecie());
        animal.put("cor",a.getCor());
        animal.put("cauda",a.getCauda());
        animal.put("pelagem",a.getPelagem());
        animal.put("altura",a.getAltura());
        animal.put("chip",a.getChip());
        animal.put("castracao",a.isCastracao());
        animal.put("observacoes",a.getObservacoes());
        animal.put("cliente_nome",c.getNome());
        animal.put("cliente_email",c.getEmail());
        animal.put("cliente_id",c.getId());
        return  ResponseEntity.accepted().body(animal.toString());
    }

    @CrossOrigin
    @PostMapping("/clinica/imunizacao")
    public ResponseEntity<?> getImunizacao(@RequestBody String body) throws JSONException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal a = animalService.getAnimalById(node.get("id").asInt());
        if(a==null){
            return ResponseEntity.badRequest().body("Utente não encontrado!");
        }
        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(a.getId());
        if(imunizacoes.size()==0){
            return ResponseEntity.badRequest().body("Não foi possível obter imunizações!");
        }
        return  ResponseEntity.accepted().body(imunizacoes);
    }

    @CrossOrigin
    @PostMapping("/clinica/intervencao")
    public ResponseEntity<?> getIntervencao(@RequestBody String body) throws JSONException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal a = animalService.getAnimalById(node.get("id").asInt());
        if(a==null){
            return ResponseEntity.badRequest().body("Utente não encontrado!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesAnimal(a.getId());
        if(intervencoes.size()==0){
            return ResponseEntity.badRequest().body("Não foi possível obter intervencões!");
        }
        return  ResponseEntity.accepted().body(intervencoes);
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
    @PostMapping("/clinica/intervencao/alterar")
    public ResponseEntity<?> cancelarIntervencao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);

        int id_intervencao = node.get("id").asInt();
        String estado = node.get("estado").asText();
        Intervencao intervencao = intervencaoService.getIntervencao(id_intervencao);

        if(intervencao==null){
            return ResponseEntity.badRequest().body("Erro a cancelar intervenção! Intervençao nao existe!");
        }

        intervencao.setEstado(estado);
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