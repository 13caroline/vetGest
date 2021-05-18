package com.example.demo.Service;

import com.example.demo.Entity.Dica;
import com.example.demo.Repository.DicaRepository;
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
