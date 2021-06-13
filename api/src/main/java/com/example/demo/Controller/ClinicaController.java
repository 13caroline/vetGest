package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Security.Utils;
import com.example.demo.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClinicaController<RandomStringUtils, RandomStringGenerator> {
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
    @Autowired
    private InternamentoService internamentoService;
    @Autowired
    private MailService mailSender;
    @Autowired
    private Utils utils;

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
    @PostMapping(path = "/clinica")
    public ResponseEntity<?> getClinica(@RequestBody  Clinica clinica){
        Clinica clinica1 = clinicaService.getClinicaByEmail(clinica.getEmail());
        if(clinica1==null){
            return ResponseEntity.badRequest().body("Utilizador Clinica não existe");
        }
        return  ResponseEntity.accepted().body(clinica1);
    }

    @CrossOrigin
    @PostMapping(path = "/clinica/editar")
    public ResponseEntity<?> editClinica(@RequestBody Clinica _clinica){
        Clinica clinica = clinicaService.getClinicaByEmail(_clinica.getEmail());
        if(clinica==null){
            return ResponseEntity.badRequest().body("Clinica nao existe!");
        }
        clinica.setNome(_clinica.getNome());
        clinica.setMorada(_clinica.getMorada());
        clinica.setConcelho(_clinica.getConcelho());
        clinica.setFreguesia(_clinica.getFreguesia());
        clinica.setContacto(_clinica.getContacto());
        clinica.setNif(_clinica.getNif());
        clinica.setIban(_clinica.getIban());
        if(!(_clinica.getPassword().equals(clinica.getPassword()))) {
            clinica.setPassword(_clinica.getPassword());
            clinicaService.saveClinica(clinica);
        }else
            clinicaService.updateClinica(clinica);
        return  ResponseEntity.accepted().body("Utilizador registado com Sucesso");
    }

    @CrossOrigin
    @GetMapping("/clinica/consultas")
    public ResponseEntity<?> getConsultas(){
        List<Intervencao> intervencoes = intervencaoService.getAllConsultas();

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

        return  ResponseEntity.accepted().body(clientes);
    }

    @CrossOrigin
    @GetMapping("/clinica/utentes")
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
                    a.put("path",animal.getPath());
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
        animal.put("path",a.getPath());
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
        return  ResponseEntity.accepted().body(intervencoes);
    }

    @CrossOrigin
    @PostMapping("/clinica/intervencoes")
    public ResponseEntity<?> getIntervencoes(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String tipo = node.get("tipo").asText();
        if(tipo.equals("Consulta")){
            List<Intervencao> consultas = intervencaoService.findAllByTipo(tipo);
            return ResponseEntity.accepted().body(consultas);
        }

        if(tipo.equals("Cirurgia")){
            List<Intervencao> cirurgias = intervencaoService.findAllByTipo(tipo);
            return ResponseEntity.accepted().body(cirurgias);
        }
        return  ResponseEntity.badRequest().body("Não foi possível obter intervencões!");
    }

    @CrossOrigin
    @GetMapping("/clinica/intervencoes/pedidos")
    public ResponseEntity<?> getIntervencoesPendentes(){
        List<Intervencao> intervencoes = intervencaoService.findAllByTipoAndEstado("Consulta","Pendente");

        return  ResponseEntity.accepted().body(intervencoes);
    }


    @CrossOrigin
    @PostMapping("/clinica/intervencoes/medico")
    public ResponseEntity<?> getIntervencoesByMedico(@RequestBody String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(body);
        String tipo = node.get("tipo").asText();
        int vet_id = node.get("veterinario").asInt();

        if(tipo.equals("Consulta")){
            List<Intervencao> consultas = intervencaoService.findAllByVeterinarioIdAndTipo(vet_id,tipo);
            return ResponseEntity.accepted().body(consultas);
        }

        if(tipo.equals("Cirurgia")){
            List<Intervencao> cirurgias = intervencaoService.findAllByVeterinarioIdAndTipo(vet_id,tipo);
            return ResponseEntity.accepted().body(cirurgias);
        }
        return  ResponseEntity.badRequest().body("Não foi possível obter intervencões!");
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
        String pass = utils.randomString(8);
        cliente.setPassword(pass);
        clienteService.saveCliente(cliente);

        SimpleMailMessage msg = mailSender.sendEmailregister(cliente.getEmail(),pass,cliente.getNome());
        JavaMailSender js = mailSender.getJavaMailSender();
        js.send(msg);

        return  ResponseEntity.accepted().body("Cliente Registado com sucesso");
    }

    @CrossOrigin
    @GetMapping("/clinica/medicos")
    public ResponseEntity<?> getMedicos(){
        List<Veterinario> veterinarios = veterinarioService.getAllVeterinarios();

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

        List<Intervencao> intervencoes = intervencaoService.findAllByVeterinarioIdAndEstadoOrEstado(vetId,"Agendada","A decorrer");
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
        intervencao.setData_pedido(LocalDateTime.now().toString().substring(0,16));
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
        String tipo = intervencao.getTipo();
        String mail = clienteService.findClienteByAnimais(intervencao.getAnimal()).getEmail();
        String nome = intervencao.getAnimal().getNome();

        if(estado.equals("Cancelada")) {
            SimpleMailMessage msg = mailSender.sendEmailCancelada(mail,tipo,nome,intervencao.getHora(),intervencao.getData());
            JavaMailSender js = mailSender.getJavaMailSender();
            js.send(msg);
        }
        else {
            SimpleMailMessage msg = mailSender.sendEmailConsulta(mail,tipo,nome,intervencao.getHora(),intervencao.getData());
            JavaMailSender js = mailSender.getJavaMailSender();
            js.send(msg);
        }

        intervencao.setEstado(estado);
        intervencaoService.saveIntervencao(intervencao);
        return ResponseEntity.accepted().body("Intervenção Alterada!");
    }

    @CrossOrigin
    @PostMapping("/clinica/utente/editar")
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
        animal.setPath(animalNew.getPath());
        animalService.updateAnimal(animal);

        return ResponseEntity.accepted().body("Animal editado com sucesso");
    }

    @CrossOrigin
    @PostMapping("/clinica/animal/historico/inserir")
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
    @PostMapping("/clinica/animal/historico")
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
    @GetMapping("/clinica/internamentos")
    public ResponseEntity<?> getInternamentos(){
        List<Internamento> internamentos = internamentoService.findAllByEstado("Internado");
        return ResponseEntity.accepted().body(internamentos);
    }

    @CrossOrigin
    @RequestMapping(value="/clinica/internamento/detalhes", method = RequestMethod.POST,produces="application/json")
    public ResponseEntity<?> getInternamentosByAnimal(@RequestBody String body) throws JsonProcessingException, JSONException {
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
        animal1.put("path",animal.getPath());
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
}