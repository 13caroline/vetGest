package com.example.demo.Animal;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository  extends JpaRepository<Animal,Integer> {

    Animal findByNome(String nome);
}
