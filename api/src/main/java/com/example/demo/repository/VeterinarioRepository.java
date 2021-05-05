package com.example.demo.repository;

import com.example.demo.entity.Veterinario;
import com.example.demo.service.ClienteService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario,String> {
    Veterinario findByEmail(String email);
}
