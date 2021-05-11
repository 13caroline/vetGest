package com.example.demo.Utilizador;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Utilizador")
@Table(
        name = "utilizador",
        uniqueConstraints = {
                @UniqueConstraint(name = "utilizador_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "utilizador_id_unique", columnNames = "id")
        })

public abstract class Utilizador implements Serializable {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private Long id;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;



    @Column(
            name = "password",
            nullable = false
    )
    private String password;

}