package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "unidades_competencia")
public class UnidadCompetenciaDb {
    @Id
    private String id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    private ModuloDb modulo;
}