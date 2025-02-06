package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PreguntaRepository extends JpaRepository<PreguntaDb, Long>, JpaSpecificationExecutor<PreguntaDb> {

}
