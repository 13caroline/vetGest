package com.example.demo.Service;

import com.example.demo.Entity.Cliente;
import com.example.demo.Entity.Nota;
import com.example.demo.Entity.Veterinario;
import com.example.demo.Repository.NotaRepository;
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
    private VeterinarioRepository repositoryVet;

    @Autowired
    private NotaRepository repositoryNota;

    public Veterinario saveVeterinario(Veterinario vet){
        vet.setPassword(passwordEncoder.encode(vet.getPassword()));
        return repositoryVet.save(vet);
    }
    public Veterinario updateVet(Veterinario vet){
        return repositoryVet.save(vet);
    }

    public Veterinario getVetByEmail(String email){
        return repositoryVet.findByEmail(email);
    }

    public Veterinario getVetById(int id) {
        return repositoryVet.findById(id);
    }


    public List<Veterinario> getAllVeterinarios() {
       return repositoryVet.findAll();
    }

    public Nota saveNota(Nota nota){
        return repositoryNota.save(nota);
    }

    public List<Nota> getAllNotasByIntervencao(int intervencao_id){
        return repositoryNota.findAllByIntervencao(intervencao_id);
    }
}
