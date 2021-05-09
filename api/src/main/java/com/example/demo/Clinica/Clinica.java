package com.example.demo.Clinica;

import com.example.demo.Cliente.Cliente;
import com.example.demo.Utilizador.Utilizador;
import com.example.demo.Veterinario.Veterinario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Clinica")
public class Clinica extends Utilizador implements Serializable {

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

    @Column(
            name = "nif"
    )
    private long nif;

    @OneToMany
    private List<Cliente> clientes;

    @OneToMany
    private List<Veterinario> veterinarios;

}