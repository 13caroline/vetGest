package com.example.demo.service;

import com.example.demo.entity.Veterinario;
import com.example.demo.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {
    @Autowired
    private VeterinarioRepository repository;

    public Veterinario saveVeterinario(Veterinario vet){
        return repository.save(vet);
    }

    public Veterinario getVetByEmail(String email){
        return repository.findByEmail(email);
    }
}
