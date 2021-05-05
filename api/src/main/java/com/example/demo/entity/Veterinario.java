package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Veterinario")
public class Veterinario extends Utilizador implements Serializable {

    @Column(
            name = "nome",
            nullable = false
    )
    private String nome;

    @Column(
            name = "morada",
            nullable = false
    )
    private String morada;

    @Column(
            name = "freguesia",
            nullable = false
    )
    private String freguesia;

    @Column(
            name = "concelho",
            nullable = false
    )
    private String concelho;

    @Column(
            name = "contacto",
            nullable = false
    )
    private String contacto;
}