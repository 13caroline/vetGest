package com.example.demo.Service;

import com.example.demo.Entity.Clinica;
import com.example.demo.Repository.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicaService {

    @Autowired
    private ClinicaRepository repository;

    public Clinica saveClinica(Clinica clinica) {
        return repository.save(clinica);
    }

    public Clinica getClinicaByEmail(String email){
        return repository.findByEmail(email);
    }
}