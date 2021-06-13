package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Alta")
@Table(name = "alta")
public class Alta {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "data",
            nullable = false
    )
    private String data;

    @Column(
            name = "motivo"
    )
    private String motivo;

    @Column(
            name = "historia_medica"
    )
    private String historia_medica;

    @Column(
            name = "evolucao"
    )
    private String evolucao;

    @Column(
            name = "proposta_terapeutica"
    )
    private String proposta_terapeutica;

    @Column(
            name = "orientacao"
    )
    private String orientacao;

    @OneToOne(cascade = {CascadeType.ALL})
    private Internamento internamento;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;
}
