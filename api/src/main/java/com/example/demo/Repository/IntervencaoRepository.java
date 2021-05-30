package com.example.demo.Repository;

import com.example.demo.Entity.Intervencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntervencaoRepository extends JpaRepository<Intervencao,Integer>{
    List<Intervencao> findAllByAnimalId(int animal_id);

    Intervencao findById(int id);

}
