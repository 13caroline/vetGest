package com.example.demo.Service;

import com.example.demo.Entity.Animal;
import com.example.demo.Entity.Historico;
import com.example.demo.Repository.AnimalRepository;
import com.example.demo.Repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    @Autowired
    private HistoricoRepository historicoRepository;


    public Animal saveAnimal (Animal animal){
        return repository.save(animal);
    }

    public List<Animal> saveAnimais (List<Animal> animais){
        return repository.saveAll(animais);
    }

    public List<Animal> getAnimais(){
        return repository.findAll();
    }

    public Animal getAnimalById(int id){
        return repository.findById(id).orElse(null);
    }

    public Animal getAnimalByNome(String nome){
        return repository.findByNome(nome);
    }

    public Animal updateAnimal(Animal animal){
        return repository.save(animal);
    }

    public Historico saveHistorico(Historico historico){
        return historicoRepository.save(historico);
    }

    public Historico findByAnimalId(int animal_id){
        return historicoRepository.findByAnimalId(animal_id);
    }

}
