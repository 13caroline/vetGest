package com.example.demo.repository;

import com.example.demo.entity.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizadorRepository extends JpaRepository<Utilizador,String> {

    Utilizador findByEmail(String email);
}
