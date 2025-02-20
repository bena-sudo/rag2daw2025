package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaCuestionarioDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaCuestionarioRepository extends JpaRepository<PreguntaCuestionarioDb, Long>, JpaSpecificationExecutor<PreguntaCuestionarioDb> {
    List<PreguntaCuestionarioDb> findByCuestionarioId(Long cuestionarioId);
}