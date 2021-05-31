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
}
