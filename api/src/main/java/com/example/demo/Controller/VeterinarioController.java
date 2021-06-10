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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    @Autowired
    private InternamentoService internamentoService;

    @CrossOrigin
    @PostMapping( "/medico/intervencoes")
    public ResponseEntity<?> getIntervencoes(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Veterinário não encontrado!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());
        return  ResponseEntity.accepted().body(intervencoes);
    }

    @CrossOrigin
    @PostMapping("/medico/agendar/intervencao")
    public ResponseEntity<?> addIntervencao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String email =node.get("veterinario").asText();
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

        String data = intervencao.getData();
        String hora = intervencao.getHora();

        List<Intervencao> intervencoes = intervencaoService.findAllByVeterinarioIdAndEstadoEquals(veterinario.getId(),"Agendada");
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
        intervencao.setEstado("Agendada");
        intervencao.setData_pedido(LocalDateTime.now().toString().substring(0,16));
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
                proxima_Imunizacao.setEstado("Agendada");
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

    @CrossOrigin
    @PostMapping("/medico/utente/editar")
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

    @CrossOrigin
    @PostMapping("/medico/internamentos")
    public ResponseEntity<?> getInternamentos(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        List<Internamento> internamentos = internamentoService.getAllInternamentosByVetId(veterinario.getId());
        if(internamentos.size()==0){
            return ResponseEntity.badRequest().body("Não tem internamentos efetuados!");
        }
        System.out.println("\n\nAQUI: "+internamentos);
        return ResponseEntity.accepted().body(internamentos);
    }

    @CrossOrigin
    @PostMapping("/medico/internar")
    public ResponseEntity<?> getInternamentos(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        Animal animal = animalService.getAnimalById(node.get("animal").asInt());
        Veterinario vet = veterinarioService.getVetByEmail(node.get("email").asText());
        Internamento internamento = mapper.convertValue(node.get("internamento"), Internamento.class);
        if(animal==null || vet == null || internamento ==null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }
        internamento.setVeterinario(vet);
        internamento.setAnimal(animal);
        internamento.setData(LocalDateTime.now().toString().substring(0,16));
        internamento.setEstado("Internado");
        internamentoService.saveInternamento(internamento);
        //System.out.println("\n\nAQUI: "+internamento);
        return ResponseEntity.accepted().body(internamento);
    }

    @CrossOrigin
    @PostMapping("/medico/internamento/detalhes")
    public ResponseEntity<?> getInternamento(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        String vet_email = node.get("email").asText();
        Veterinario vet = veterinarioService.getVetByEmail(vet_email);
        int id = node.get("internamentoId").asInt();
        Internamento internamento = internamentoService.findById(id);

        if(animal==null || vet == null || internamento ==null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        if(internamento.getVeterinario().getEmail().equals(vet_email) && internamento.getAnimal().getId() == id_animal) {

            List<NotaInternamento> notaInternamentos = internamentoService.findAllByInternamento(internamento);

            String nome = animal.getNome();
            String dataNascimento = animal.getDataNascimento();

            System.out.println("Aqui");
            JSONObject response = new JSONObject();
            response.put("nome", nome);
            response.put("dataNascimento", dataNascimento);
            response.put("notasInternamento", notaInternamentos);

            return ResponseEntity.accepted().body(response.toString());
        }

        return ResponseEntity.badRequest().body("Erro entre entidades e internamento");

    }

    @CrossOrigin
    @PostMapping("/medico/internamento/detalhes/adiciona")
    public ResponseEntity<?> adicionarNotaInternamento(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        String vet_email = node.get("email").asText();
        Veterinario vet = veterinarioService.getVetByEmail(vet_email);
        int id = node.get("id").asInt();
        Internamento internamento = internamentoService.findById(id);

        if(animal==null || vet == null || internamento == null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        NotaInternamento notaInternamento =  mapper.convertValue(node.get("notaInternamento"), NotaInternamento.class);
        notaInternamento.setInternamento(internamento);
        internamentoService.saveNota(notaInternamento);

        System.out.println("\n\nAQUI: Sucesso");
        return ResponseEntity.accepted().body("Nota de Internamento adicionada com sucesso!");
    }

    /*
    @CrossOrigin
    @PostMapping("/medico/internamento/alta")
    public ResponseEntity<?> getAlta(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        String vet_email = node.get("email").asText();
        Veterinario vet = veterinarioService.getVetByEmail(vet_email);
        int id = node.get("id").asInt();
        Internamento internamento = internamentoService.findById(id);

        if(animal==null || vet == null || internamento == null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        NotaInternamento notaInternamento =  mapper.convertValue(node.get("notaInternamento"), NotaInternamento.class);
        notaInternamento.setInternamento(internamento);
        internamentoService.saveNota(notaInternamento);

        System.out.println("\n\nAQUI: Sucesso");
        return ResponseEntity.accepted().body("Nota de Internamento adicionada com sucesso!");
    }*/
}

