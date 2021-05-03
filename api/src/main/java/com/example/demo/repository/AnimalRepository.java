package com.example.demo.repository;

import com.example.demo.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository  extends JpaRepository<Animal,Integer> {

    Animal findByNome(String nome);
}
