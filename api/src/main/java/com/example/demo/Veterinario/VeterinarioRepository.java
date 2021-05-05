package com.example.demo.Veterinario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario,String> {
    Veterinario findByEmail(String email);
}
