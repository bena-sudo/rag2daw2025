package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UnidadCompetenciaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadCompetenciaRepository extends JpaRepository<UnidadCompetenciaDb, String>, JpaSpecificationExecutor<UnidadCompetenciaDb> {
    // Optional<UnidadCompetenciaDb> findByNombreIgnoreCase(String nombre);
    List<UnidadCompetenciaDb> findByModuloId(Long moduloId);
}
