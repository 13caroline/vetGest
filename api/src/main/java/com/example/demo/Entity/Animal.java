package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Animal")
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "nome",
            nullable = false
    )
    private String nome;

    @Column(
            name = "raca",
            nullable = false
    )
    private String raca;

    @Column(
            name = "data_nascimento",
            nullable = false
    )
    private String dataNascimento;

    @Column(
            name = "sexo",
            nullable = false
    )
    private String sexo;

    @Column(
            name = "especie",
            nullable = false
    )
    private String especie;

    @Column(
            name = "cor",
            nullable = false
    )
    private String cor;

    @Column(
            name = "cauda"
    )
    private String cauda;

    @Column(
            name = "pelagem"
    )
    private String pelagem;

    @Column(
            name = "altura"
    )
    private int altura; //altura em cm

    @Column(
            name = "chip"
    )
    private String chip;

    @Column(
            name = "castracao",
            nullable = false
    )
    private boolean castracao;

    @Column(
            name = "observacoes",
            nullable = false
    )
    private String observacoes;

}
