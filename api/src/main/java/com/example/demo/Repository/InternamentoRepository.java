package com.example.demo.Repository;

import com.example.demo.Entity.Internamento;
import com.example.demo.Entity.Intervencao;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface InternamentoRepository extends JpaRepository<Internamento,Integer>{
    List<Internamento> findAllByAnimalId(int animal_id);

    Internamento findById(int id);

    List<Internamento> findAllByVeterinarioId(int veterinario_id);

    List<Internamento> findAllByVeterinarioEmailAndAnimalId(String vet_email, int animal_id);

    List<Internamento> findAllByVeterinarioEmailAndIdAndAnimalId(String vet_email, int internamento_id ,int animal_id);
}
