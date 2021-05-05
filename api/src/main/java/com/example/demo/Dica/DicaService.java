package com.example.demo.Dica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicaService {

    @Autowired
    private DicaRepository repository;

    public Dica saveDica(Dica dica){
        return repository.save(dica);
    }
}
