package com.example.demo.Service;

import com.example.demo.Entity.Animal;
import com.example.demo.Repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

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
}
