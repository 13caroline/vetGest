package com.example.demo.Utilizador;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Utilizador")
@Table(
        name = "utilizador",
        uniqueConstraints = {
                @UniqueConstraint(name = "utilizador_email_unique", columnNames = "email")
        })

public abstract class Utilizador implements Serializable {
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

}
