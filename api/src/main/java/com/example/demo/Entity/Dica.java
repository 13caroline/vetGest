package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Dica")
@Table(name = "dica")
public class Dica {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "dica",
            nullable = false
    )
    private String dica;

    @Column(
            name = "data",
            nullable = false
    )
    private String data;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;

    public Dica(Dica dica) {
        this.data = dica.getData();
        this.dica = dica.getDica();
    }
}