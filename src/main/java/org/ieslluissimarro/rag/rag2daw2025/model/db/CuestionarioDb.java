package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cuestionarios")
public class CuestionarioDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;

    @OneToOne
    @JoinColumn(name = "unidad_competencia_id")
    private UnidadCompetenciaDb unidadCompetencia;

    @OneToMany(mappedBy = "cuestionario")
    private List<PreguntaCuestionarioDb> preguntas;
}