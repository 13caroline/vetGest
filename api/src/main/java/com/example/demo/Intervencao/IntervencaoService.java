package com.example.demo.Intervencao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervencaoService {

    @Autowired
    private IntervencaoRepository repository;

    public Intervencao saveIntervencao(Intervencao intervencao){
        return repository.save(intervencao);
    }
}
