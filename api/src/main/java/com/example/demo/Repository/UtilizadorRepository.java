package com.example.demo.Repository;

import com.example.demo.Entity.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizadorRepository extends JpaRepository<Utilizador,String> {

    Utilizador findByEmail(String email);
}
