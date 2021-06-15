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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
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
    @PostMapping("/medico")
    public ResponseEntity<?> getMedico(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Veterinário não encontrado!");
        }
        return  ResponseEntity.accepted().body(veterinario);
    }

    @CrossOrigin
    @PostMapping( "/medico/intervencoes")
    public ResponseEntity<?> getIntervencoes(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Veterinário não encontrado!");
        }
        List<Intervencao> intervencoes = intervencaoService.findAllByVeterinarioIdAndEstadoOrEstado(veterinario.getId(),"Agendada", "A decorrer");
        return  ResponseEntity.accepted().body(intervencoes);
    }
    
    @CrossOrigin
    @PostMapping("/medico/intervencao")
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

        List<Intervencao> intervencoes = intervencaoService.findAllByVeterinarioIdAndEstadoOrEstado(veterinario.getId(),"Agendada","A decorrer");
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
    @GetMapping("/medico/utentes")
    public ResponseEntity<?> getUtentes(){
        List<Cliente> clientes = clienteService.getClientes();

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
                    a.put("image",animal.getImage());
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
    @PostMapping("/medico/intervencao/alterar")
    public ResponseEntity<?> cancelarIntervencao(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);

        int id_intervencao = node.get("id").asInt();
        String estado = node.get("estado").asText();
        String observacoes = node.get("observacoes").asText();

        Intervencao intervencao = intervencaoService.getIntervencao(id_intervencao);

        if(intervencao==null){
            return ResponseEntity.badRequest().body("Erro a obter intervencao");
        }
        if(!(observacoes==null))
            intervencao.setObservacoes(observacoes);

        intervencao.setEstado(estado);
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervenção Alterada");
    }

    @CrossOrigin
    @PostMapping("/medico/consultas")
    public ResponseEntity<?> getConsultas(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        if(veterinario==null ){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        List<Intervencao> intervencoes = intervencaoService.getIntervencoesVeterinario(veterinario.getId());

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
        animal.put("image",a.getImage());
        animal.put("cliente_nome",c.getNome());
        animal.put("cliente_email",c.getEmail());
        animal.put("cliente_id",c.getId());
        return  ResponseEntity.accepted().body(animal.toString());
    }
    
    @CrossOrigin
    @PostMapping("/medico/imunizacao")
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
        Veterinario veterinario = veterinarioService.getVetByEmail(node.get("email").asText());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Erro a obter veterinário!");
        }
        imunizacao.setAnimal(animal);
        imunizacao.setVeterinario(veterinario);
        
        String data = imunizacao.getData();
        LocalDate date = LocalDate.parse(data);

        if(date.isAfter(LocalDate.now()))
            imunizacao.setEstado("Atualizada");
        else
            imunizacao.setEstado("Administrada");

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
            imunizacao.setProxImunizacao(null);
            imunizacaoService.saveImunizacao(imunizacao);
            imunizacaoService.saveImunizacao(proximaImunizacao);
            imunizacao.setProxima_imunizacao(proximaImunizacao);
            imunizacaoService.saveImunizacao(imunizacao);
        }
        imunizacaoService.saveImunizacao(imunizacao);
        return ResponseEntity.accepted().body("Imunização agendada!");
    }

    @CrossOrigin
    @PostMapping("/medico/utente/vacinas")
    public ResponseEntity<?> getVacinas(@RequestBody Animal id){
        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(id.getId());

        List<Imunizacao> vacinas = new ArrayList<>();
        imunizacoes.forEach(imunizacao -> {
            if(imunizacao.getTipo().equals("Vacina"))
            {
                vacinas.add(imunizacao);
            }
        });
        return ResponseEntity.accepted().body(vacinas);
        return ResponseEntity.accepted().body(vacinas);
    }

    @CrossOrigin
    @PostMapping("/medico/utente/desparasitacoes")
    public ResponseEntity<?> getDesparasitacoes(@RequestBody Animal id){
        List<Imunizacao> imunizacoes = imunizacaoService.getImunizacoes(id.getId());

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

        imunizacao.setEstado("Administrada");
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
    @PostMapping("/medico/animal/historico")
    public ResponseEntity<?> getHistorico(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int animal_id = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(animal_id);

        if(animal ==null){
            return ResponseEntity.badRequest().body("Erro no Animal ou Historico!");
        }
        Historico historico = animalService.findByAnimalId(animal_id);

        return ResponseEntity.accepted().body(historico);
    }

    @CrossOrigin
    @PostMapping("/medico/animal/historico/inserir")
    public ResponseEntity<?> addHistorico(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int animal_id = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(animal_id);
        Historico historico = mapper.convertValue(node.get("historico"),Historico.class);

        if(historico==null || animal ==null){
            return ResponseEntity.badRequest().body("Erro no Animal ou Historico!");
        }

        historico.setAnimal(animal);
        animalService.saveHistorico(historico);

        return ResponseEntity.accepted().body("Historico Adicionado!");
    }

    @CrossOrigin
    @PostMapping("/medico/animal/historico/alterar")
    public ResponseEntity<?> editHistorico(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int animal_id = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(animal_id);
        Historico historico = mapper.convertValue(node.get("historico"),Historico.class);

        if(historico==null || animal ==null){
            return ResponseEntity.badRequest().body("Erro no Animal ou Historico!");
        }
        Historico historico1 = animalService.findByAnimalId(animal_id);

        historico1.setAlergias(historico.getAlergias());
        historico1.setTransfusoes(historico.getTransfusoes());
        historico1.setTipo_alergias(historico.getTipo_alergias());
        historico1.setHistoria_medica(historico.getHistoria_medica());
        historico1.setHistoria_ginecologica(historico.getHistoria_ginecologica());
        historico1.setMedicacao(historico.getMedicacao());
        historico1.setAntecedentes(historico.getAntecedentes());

        animalService.saveHistorico(historico1);

        return ResponseEntity.accepted().body("Historico Alterado!");
    }
    @CrossOrigin
    @PostMapping("/medico/internamentos")
    public ResponseEntity<?> getInternamentos(@RequestBody Veterinario email){
        Veterinario veterinario = veterinarioService.getVetByEmail(email.getEmail());
        List<Internamento> _internamentos = internamentoService.getAllInternamentosByVetId(veterinario.getId());
        List <Internamento> internamentos = new ArrayList<>();
        _internamentos.forEach(internamento -> {
            if(internamento.getEstado().equals("Internado")){
                internamentos.add(internamento);
            }
        });

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
        Intervencao intervencao = intervencaoService.getIntervencao(node.get("intervencao").asInt());
        if(animal==null || vet == null || internamento ==null || intervencao ==null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }
        internamento.setIntervencao(intervencao);
        internamento.setVeterinario(vet);
        internamento.setAnimal(animal);
        internamento.setData(LocalDateTime.now().toString().substring(0,16));
        internamento.setEstado("Internado");
        internamentoService.saveInternamento(internamento);
        //System.out.println("\n\nAQUI: "+internamento);
        return ResponseEntity.accepted().body(internamento);
    }

    @CrossOrigin
    @RequestMapping(value="/medico/internamento/detalhes", method = RequestMethod.POST,produces="application/json")
    public ResponseEntity<?> getInternamento(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("id").asInt();
        Animal animal = animalService.getAnimalById(id_animal);

        if(animal==null)
            return ResponseEntity.badRequest().body("Animal nao exite");

        Internamento internamento = internamentoService.findByAnimalIdAndEstado(id_animal,"Internado");
        List<NotaInternamento> notaInternamentos = internamentoService.findAllByInternamento(internamento);

        System.out.println(notaInternamentos);
        JSONObject response = new JSONObject();
        JSONObject animal1 = new JSONObject();
        animal1.put("id",animal.getId());
        animal1.put("nome",animal.getNome());
        animal1.put("raca",animal.getRaca());
        animal1.put("dataNascimento",animal.getDataNascimento());
        animal1.put("sexo",animal.getSexo());
        animal1.put("especie",animal.getEspecie());
        animal1.put("cor",animal.getCor());
        animal1.put("cauda",animal.getCauda());
        animal1.put("pelagem",animal.getPelagem());
        animal1.put("altura",animal.getAltura());
        animal1.put("chip",animal.getChip());
        animal1.put("castracao",animal.isCastracao());
        animal1.put("observacoes",animal.getObservacoes());
        response.put("animal",animal1);
        notaInternamentos.forEach(notaInternamento -> {
            try {
                JSONObject nota = new JSONObject();
                nota.put("descricao", notaInternamento.getDescricao());
                nota.put("data", notaInternamento.getData());
                response.accumulate("notas",nota);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.accepted().body(response.toString());
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
        Internamento internamento = internamentoService.findByAnimalIdAndEstado(id_animal,"Internado");

        if(animal==null || vet == null || internamento == null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        NotaInternamento notaInternamento =  mapper.convertValue(node.get("notaInternamento"), NotaInternamento.class);
        notaInternamento.setInternamento(internamento);
        notaInternamento.setData(LocalDateTime.now().toString().substring(0,16));
        internamentoService.saveNota(notaInternamento);

        System.out.println("\n\nAQUI: Sucesso");
        return ResponseEntity.accepted().body("Nota de Internamento adicionada com sucesso!");
    }


    @CrossOrigin
    @PostMapping("/medico/internamento/alta")
    public ResponseEntity<?> getAlta(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        String vet_email = node.get("email").asText();
        Veterinario vet = veterinarioService.getVetByEmail(vet_email);

        Internamento internamento = internamentoService.findByAnimalIdAndEstado(id_animal, "Internado");

        if(animal==null || vet == null || internamento == null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        System.out.println("\n\nAQUI: Sucesso" + internamento);
        return ResponseEntity.accepted().body(internamento);
    }

    @CrossOrigin
    @PostMapping("/medico/internamento/alta/confirma")
    public ResponseEntity<?> registaAlta(@RequestBody String body) throws JsonProcessingException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_animal = node.get("animal").asInt();
        Animal animal = animalService.getAnimalById(id_animal);
        String vet_email = node.get("email").asText();
        Veterinario vet = veterinarioService.getVetByEmail(vet_email);
        Internamento internamento = internamentoService.findByAnimalIdAndEstado(id_animal, "Internado");

        Alta alta = mapper.convertValue(node.get("alta"), Alta.class);

        if(animal==null || vet == null || internamento == null || alta==null){
            return ResponseEntity.badRequest().body("Alguma das Entidades nao existe!");
        }

        internamento.setEstado("Alta");
        alta.setInternamento(internamento);
        internamentoService.saveAlta(alta);
        System.out.println(alta);
        return ResponseEntity.accepted().body("Alta com Sucesso");
    }

    @CrossOrigin
    @PostMapping("/medico/cirurgia/notas")
    public ResponseEntity<?> getNotasCirurgia(@RequestBody String body) throws JSONException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        int id_cirurgia = node.get("id").asInt();
        Intervencao cirurgia = intervencaoService.getIntervencao(id_cirurgia);
        JSONObject response = new JSONObject();
        JSONObject vet = new JSONObject();
        vet.put("id",cirurgia.getVeterinario().getId());
        vet.put("nome",cirurgia.getVeterinario().getNome());
        response.put("veterinario",vet);
        Internamento internamento = internamentoService.findByAnimalIdAndEstado(cirurgia.getAnimal().getId(),"Internado");
        System.out.println("\n\nAQUI: "+internamento);
        List<NotaInternamento> notaInternamentos = internamentoService.findAllByInternamento(internamento);
        JSONObject nota = new JSONObject();
        notaInternamentos.forEach(notaInternamento -> {
            try {

                nota.put("descricao", notaInternamento.getDescricao());
                nota.put("data", notaInternamento.getData());
                response.accumulate("notas",nota);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        JSONObject inter = new JSONObject();
        inter.put("data",cirurgia.getData());
        inter.put("hora",cirurgia.getHora());
        inter.put("observacoes",cirurgia.getObservacoes());
        response.put("cirurgia",inter);
        return ResponseEntity.accepted().body(response.toString());
    }

    @CrossOrigin
    @PostMapping("/medico/preferencias")
    public ResponseEntity<?> editarPreferencias(@RequestBody Veterinario vet){
        Veterinario veterinario = veterinarioService.getVetByEmail(vet.getEmail());
        if(veterinario==null){
            return ResponseEntity.badRequest().body("Veterinário não existe!");
        }
        veterinario.setNome(vet.getNome());
        veterinario.setMorada(vet.getMorada());
        veterinario.setConcelho(vet.getConcelho());
        veterinario.setFreguesia(vet.getFreguesia());
        veterinario.setContacto(vet.getContacto());
        if(!(vet.getPassword().equals(veterinario.getPassword()))) {
            veterinario.setPassword(vet.getPassword());
            veterinarioService.saveVeterinario(veterinario);
        }else
            veterinarioService.saveVeterinario(veterinario);

        return  ResponseEntity.accepted().body("Dados alterados com sucesso!");
    }

    @CrossOrigin
    @PostMapping("/medico/adicionaFoto")
    public ResponseEntity<?> addFoto(@RequestParam("imageFile") MultipartFile file,@RequestParam("userid") int userid) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Veterinario vet = veterinarioService.getVetById(userid);
        vet.setImage(file.getBytes());
        veterinarioService.saveVeterinario(vet);
        return ResponseEntity.accepted().body("Imagem alterada com sucesso!");
    }
}

