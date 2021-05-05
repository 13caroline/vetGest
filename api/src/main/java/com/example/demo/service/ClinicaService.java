package com.example.demo.service;

import com.example.demo.entity.Clinica;
import com.example.demo.repository.ClinicaRepository;
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