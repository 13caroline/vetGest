package com.example.demo.Service;

import com.example.demo.Entity.Intervencao;
import com.example.demo.Repository.IntervencaoRepository;
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
