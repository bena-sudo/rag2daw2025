package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.SectorDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<SectorDb, Long>, JpaSpecificationExecutor<SectorDb> {
}
