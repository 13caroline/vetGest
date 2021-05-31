package com.example.demo.Repository;

import com.example.demo.Entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario,String> {
    Veterinario findByEmail(String email);

    Veterinario findById(int id);
}
