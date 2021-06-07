package com.example.demo.Service;

import com.example.demo.Entity.Internamento;
import com.example.demo.Repository.InternamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternamentoService {
    @Autowired
    private InternamentoRepository repository;

    public List<Internamento> getAllInternamentosByVetId(int veterinario_id) {
        return repository.findAllByVeterinarioId(veterinario_id);
    }


    public List<Internamento> getInternamentoByAnimalId(int animal_id) {
        return repository.findAllByAnimalId(animal_id);
    }
}
