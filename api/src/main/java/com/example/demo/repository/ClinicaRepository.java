package com.example.demo.repository;


import com.example.demo.entity.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica,String> {
    Clinica findByEmail(String email);
}
