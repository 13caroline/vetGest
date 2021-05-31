package com.example.demo.Entity;

import java.util.List;

public class AnimalIntervencoes {

    private Animal animal;
    private List<Intervencao> intervencoes;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public List<Intervencao> getIntervencoes() {
        return intervencoes;
    }

    public void setIntervencoes(List<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }
}