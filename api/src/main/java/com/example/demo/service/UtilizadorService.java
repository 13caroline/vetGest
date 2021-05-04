package com.example.demo.service;

import com.example.demo.entity.Utilizador;
import com.example.demo.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UtilizadorService {

    @Autowired
    private UtilizadorRepository repository;

    public Utilizador saveUtilizador(Utilizador utilizador){
        return repository.save(utilizador);
    }

    public List<Utilizador> getUtilizadores(){
        return repository.findAll();
    }

    public Utilizador getUtilizadorByEmail(String email){
        return repository.findByEmail(email);
    }

}
