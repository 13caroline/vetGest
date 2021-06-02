package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "JWToken")
@Table(name = "jwtoken")
public class JWToken {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "token",
            nullable = false
    )
    private String token;

    @Column(
            name = "data_expiracao",
            nullable = false
    )
    private Date data_expiracao;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;


    public JWToken(String jwt, Date extractExpiration, String email) {
        this.token=jwt;
        this.data_expiracao=extractExpiration;
        this.email=email;
    }
}

