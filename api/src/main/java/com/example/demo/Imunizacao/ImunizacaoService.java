package com.example.demo.Imunizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImunizacaoService {

    @Autowired
    private ImunizacaoRepository repository;

    public Imunizacao saveImunizacao(Imunizacao imunizacao){
        return repository.save(imunizacao);
    }
}
