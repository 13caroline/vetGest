package com.example.demo.Repository;

import com.example.demo.Entity.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface JWTokenRepository extends JpaRepository<JWToken,Long> {

    JWToken findByToken(String token);
}
