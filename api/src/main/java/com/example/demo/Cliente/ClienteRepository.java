package com.example.demo.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    Cliente findByEmail(String email);
}