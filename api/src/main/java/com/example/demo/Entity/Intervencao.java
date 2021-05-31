package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Intervencao")
@Table(name = "intervencao")
public class Intervencao {
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
            name = "hora",
            nullable = false
    )
    private String hora;

    @Column(
            name = "descricao"
    )
    private String descricao;

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

    @ManyToOne(cascade = {CascadeType.ALL})
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Veterinario veterinario;

    public Intervencao(Intervencao intervencao) {
        this.data = intervencao.getData();
        this.descricao = intervencao.getDescricao();
        this.hora = intervencao.getHora();
        this.tipo = intervencao.getTipo();
        this.estado = intervencao.getEstado();
        this.motivo = intervencao.getMotivo();
    }
}