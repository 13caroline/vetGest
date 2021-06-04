package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Nota")
public class Nota {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "descricao",
            nullable = false
    )
    private String descricao;

    @Column(
            name = "data",
            nullable = false
    )
    private String data;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Intervencao intervencao;
}
