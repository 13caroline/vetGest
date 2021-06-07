package com.example.demo.Repository;

import com.example.demo.Entity.Animal;
import com.example.demo.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    Cliente findByEmail(String email);

}
