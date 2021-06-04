package com.example.demo.Repository;

import com.example.demo.Entity.Nota;
import com.example.demo.Entity.NotaInternamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaInternamentoRepository extends JpaRepository<NotaInternamento, Integer> {
    List<NotaInternamento> findAllByInternamento(int internamento);
}
