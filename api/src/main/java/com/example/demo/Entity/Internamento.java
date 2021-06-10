package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Internamento")
@Table(name = "internamento")
public class Internamento {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    private int id;

    @Column(
            name = "data",
            nullable = false
    )
    private String  data;

    @Column(
            name = "localizacao",
            nullable = false
    )
    private String localizacao;

    @Column(
            name = "motivo",
            nullable = false
    )
    private String motivo;

    @Column(
            name = "estado",  //"Internado" || "Com Alta"
            nullable = false
    )
    private String estado;

    @Column(
            name = "nota"
    )
    private String nota;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;

}