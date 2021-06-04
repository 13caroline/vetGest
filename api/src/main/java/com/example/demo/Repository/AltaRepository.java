package com.example.demo.Repository;

import com.example.demo.Entity.Alta;
import com.example.demo.Entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AltaRepository extends JpaRepository<Alta,Integer> {

    Alta findById(int id);
}
