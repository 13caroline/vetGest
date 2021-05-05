package com.example.demo.Utilizador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizadorRepository extends JpaRepository<Utilizador,String> {

    Utilizador findByEmail(String email);
}
