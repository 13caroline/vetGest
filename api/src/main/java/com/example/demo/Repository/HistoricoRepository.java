package com.example.demo.Repository;

import com.example.demo.Entity.Historico;
import com.example.demo.Entity.Imunizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico,Integer>{

}
