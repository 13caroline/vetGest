package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Imunizacao")
@Table(name = "imunizacao")
public class Imunizacao {
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
            name = "data_toma"
    )
    private String  data_toma;

    @Column(
            name = "prox_imunizacao"
    )
    private String proxImunizacao;

    @Column(
            name = "estado",
            nullable = false
    )
    private String estado;

    @Column(
            name = "tipo",
            nullable = false
    )
    private String tipo;

    @Column(
            name = "vacina"
    )
    private String vacina;

    @Column(
            name = "observacoes"
    )
    private String observacoes;

    @Column(
            name = "tratamento"
    )
    private String tratamento;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;

    @ManyToOne(cascade = {CascadeType.ALL},
               optional = true)
    private Imunizacao proxima_imunizacao;

    public Imunizacao(Imunizacao imunizacao) {
        this.data = imunizacao.getData();
        this.data_toma=imunizacao.getData_toma();
        this.proxImunizacao = imunizacao.getProxImunizacao();
        this.tipo = imunizacao.getTipo();
        this.estado = imunizacao.getEstado();
        this.observacoes = imunizacao.getObservacoes();
        this.tratamento = imunizacao.getTratamento();
        this.vacina = imunizacao.getVacina();
    }
}