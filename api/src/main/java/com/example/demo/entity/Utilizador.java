package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Utilizador")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(
        name = "utilizador",
        uniqueConstraints = {
                @UniqueConstraint(name = "utilizador_email_unique", columnNames = "email")
        })

public class Utilizador {
    @Id
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

    @Column(
            name = "tipo",
            nullable = false
    )
    private int tipo;
}
