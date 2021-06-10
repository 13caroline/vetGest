package com.example.demo.Service;

import com.example.demo.Entity.Internamento;
import com.example.demo.Entity.NotaInternamento;
import com.example.demo.Repository.InternamentoRepository;
import com.example.demo.Repository.NotaInternamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternamentoService {
    @Autowired
    private InternamentoRepository repository;

    @Autowired
    private NotaInternamentoRepository repositoryNota;

    public List<Internamento> getAllInternamentosByVetId(int veterinario_id) {
        return repository.findAllByVeterinarioId(veterinario_id);
    }

    public List<Internamento> getInternamentoByAnimalId(int animal_id) {
        return repository.findAllByAnimalId(animal_id);
    }

    public List<Internamento> findAllByVeterinarioEmailAndAnimalId(String vet_email, int animal_id){
        return repository.findAllByVeterinarioEmailAndAnimalId(vet_email, animal_id);
    }

    public Internamento saveInternamento(Internamento internamento){
        return repository.save(internamento);
    }

    public List<Internamento> findAllByVeterinarioEmailAndIdAndAnimalId(String vet_email, int internamento_id ,int animal_id){
        return repository.findAllByVeterinarioEmailAndIdAndAnimalId(vet_email,internamento_id,animal_id);
    }

    public List<NotaInternamento> findAllByInternamento(Internamento internamento){
        return repositoryNota.findAllByInternamento(internamento);
    }

    public Internamento findById(int internamento){
        return repository.findById(internamento);
    }

    public NotaInternamento saveNota(NotaInternamento nota){
        return repositoryNota.save(nota);
    }

    public List<Internamento> findAllByEstado(String estado){
        return repository.findAllByEstado(estado);
    }

    public  Internamento findByAnimalIdAndEstado(int id_animal, String estado){
        return repository.findByAnimalIdAndEstado(id_animal, estado);
    };

}
