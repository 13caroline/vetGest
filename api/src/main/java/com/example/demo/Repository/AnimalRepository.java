package com.example.demo.Repository;

import com.example.demo.Entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository  extends JpaRepository<Animal,Integer> {

    Animal findByNome(String nome);
}
