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
            name = "prox_imunizacao"
    )
    private String proxImunizacao;

    @Column(
            name = "estado",
            nullable = false
    )
    private String estado;

    @Column(
            name = "motivo"
    )
    private String motivo;

    @Column(
            name = "tipo",
            nullable = false
    )
    private String tipo;

    @Column(
            name = "vacina"
    )
    private String vacina;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;

    public Imunizacao(Imunizacao imunizacao) {
        this.data = imunizacao.getData();
        this.proxImunizacao = imunizacao.getProxImunizacao();
        this.tipo = imunizacao.getTipo();
        this.estado = imunizacao.getEstado();
        this.motivo = imunizacao.getMotivo();
    }
}