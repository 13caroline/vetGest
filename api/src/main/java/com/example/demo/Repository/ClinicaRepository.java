package com.example.demo.Repository;


import com.example.demo.Entity.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica,String> {
    Clinica findByEmail(String email);
}
