package com.example.demo.Entity;

import java.util.List;

public class ClienteIntervencoes {

    private Cliente cliente;
    private List<Intervencao> intervencoes;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Intervencao> getIntervencoes() {
        return intervencoes;
    }

    public void setIntervencoes(List<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }
}