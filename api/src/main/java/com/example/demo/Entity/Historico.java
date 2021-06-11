package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Historico")
@Table(name = "historico")
public class Historico {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "alergias",
            nullable = false
    )
    private Boolean alergias;

    @Column(
            name = "transfusoes",
            nullable = false
    )
    private Boolean transfusoes;

    @Column(
            name = "tipo_alergias"
    )
    private String tipo_alergias;

    @Column(
            name = "historia_medica"
    )
    private String historia_medica;

    @Column(
            name = "historia_ginecologica"
    )
    private String historia_ginecologica;

    @Column(
            name = "medicacao"
    )
    private String medicacao;

    @Column(
            name = "antecedentes"
    )
    private String antecedentes;

    @OneToOne(cascade = {CascadeType.ALL})
    private Animal animal;
}