package com.example.demo.Repository;

import com.example.demo.Entity.Internamento;
import com.example.demo.Entity.Nota;
import com.example.demo.Entity.NotaInternamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaInternamentoRepository extends JpaRepository<NotaInternamento, Integer> {
    List<NotaInternamento> findAllByInternamento(Internamento internamento);
}
