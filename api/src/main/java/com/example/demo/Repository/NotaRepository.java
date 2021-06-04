package com.example.demo.Repository;

import com.example.demo.Entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findAllByIntervencao(int intervencao_id);
}
