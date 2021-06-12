package com.example.demo.Repository;

import com.example.demo.Entity.Intervencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IntervencaoRepository extends JpaRepository<Intervencao,Integer>{
    List<Intervencao> findAllByAnimalId(int animal_id);

    Intervencao findById(int id);

    List<Intervencao> findAllByVeterinarioId(int veterinario_id);

    List<Intervencao> findAllByVeterinarioIdAndEstadoEquals(int vet_id, String estado);

    List<Intervencao> findAllByTipo(String tipo);

    List<Intervencao> findByAnimalIdAndTipo(int id, String tipo);

    List<Intervencao> findAllByVeterinarioIdAndTipo(int vet_id, String estado);

    List<Intervencao> findAllByVeterinarioIdAndEstadoOrEstado(int vet_id, String estado, String estado1);

    List<Intervencao> findAllByTipoAndEstado(String tipo, String estado);
}
