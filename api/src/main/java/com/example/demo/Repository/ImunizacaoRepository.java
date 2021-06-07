package com.example.demo.Repository;

import com.example.demo.Entity.Imunizacao;
import com.example.demo.Entity.Intervencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImunizacaoRepository extends JpaRepository<Imunizacao,Integer>{
    List<Imunizacao> findAllByAnimalId(int animal_id);
    Imunizacao findById(int id);
}
