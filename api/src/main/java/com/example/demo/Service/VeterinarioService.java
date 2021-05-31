package com.example.demo.Service;

import com.example.demo.Entity.Veterinario;
import com.example.demo.Repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VeterinarioRepository repository;

    public Veterinario saveVeterinario(Veterinario vet){
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
        return repository.save(vet);
    }

    public Veterinario getVetByEmail(String email){
        return repository.findByEmail(email);
    }

    public Veterinario getVetById(int id) {
        return repository.findById(id);
    }


    public List<Veterinario> getAllVeterinarios() {
       return repository.findAll();
    }
}
