package com.example.demo.Service;


import com.example.demo.Entity.Intervencao;
import com.example.demo.Repository.IntervencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class IntervencaoService {

    @Autowired
    private IntervencaoRepository repository;

    public Intervencao saveIntervencao(Intervencao intervencao){
        return repository.save(intervencao);
    }

    public List<Intervencao> getIntervencoesAnimal(int animal_id){
        return repository.findAllByAnimalId(animal_id);
    }

    public Intervencao getIntervencao(int id_intervencao){
        return repository.findById(id_intervencao);
    }

    public List<Intervencao> getAllConsultas() {
        return repository.findAll();
    }

    public List<Intervencao> getIntervencoesVeterinario(int veterinario_id) {
        return repository.findAllByVeterinarioId(veterinario_id);
    }

    public List<Intervencao> findAllByVeterinarioIdAndEstadoEquals(int vet_id, String estado){
        return repository.findAllByVeterinarioIdAndEstadoEquals(vet_id,estado);
    }

    public List<Intervencao> findAllByTipo(String tipo){
        return repository.findAllByTipo(tipo);
    };

    public List<Intervencao> findAllByVeterinarioIdAndTipo(int vet_id, String estado){
        return repository.findAllByVeterinarioIdAndTipo(vet_id,estado);
    }

    public List<Intervencao> findByAnimalIdAndTipo(int id, String tipo){
        return repository.findByAnimalIdAndTipo(id,tipo);
    }

}
