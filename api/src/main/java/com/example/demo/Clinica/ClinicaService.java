package com.example.demo.Clinica;

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