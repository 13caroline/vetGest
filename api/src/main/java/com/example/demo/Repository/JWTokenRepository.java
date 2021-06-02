package com.example.demo.Repository;

import com.example.demo.Entity.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JWTokenRepository extends JpaRepository<JWToken,Long> {


    void deleteByToken(String token);

    JWToken findByToken(String token);
}
