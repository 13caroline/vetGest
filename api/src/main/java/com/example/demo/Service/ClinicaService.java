package com.example.demo.Service;

import com.example.demo.Entity.Cliente;
import com.example.demo.Entity.Clinica;
import com.example.demo.Repository.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClinicaService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClinicaRepository repository;

    public Clinica saveClinica(Clinica clinica) {
        clinica.setPassword(passwordEncoder.encode(clinica.getPassword()));
        return repository.save(clinica);
    }

    public Clinica getClinicaByEmail(String email){
        return repository.findByEmail(email);
    }

    public Clinica updateClinica(Clinica clinica){
        return repository.save(clinica);
    }
}