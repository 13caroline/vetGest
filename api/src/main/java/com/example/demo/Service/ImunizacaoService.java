package com.example.demo.Service;

import com.example.demo.Entity.Imunizacao;
import com.example.demo.Repository.ImunizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImunizacaoService {

    @Autowired
    private ImunizacaoRepository repository;

    public Imunizacao saveImunizacao(Imunizacao imunizacao){
        return repository.save(imunizacao);
    }

    public List<Imunizacao> getImunizacoes(int animal_id){
        return repository.findAllByAnimalId(animal_id);
    }
}
