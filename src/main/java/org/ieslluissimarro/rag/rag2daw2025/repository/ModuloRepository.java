package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.ModuloDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<ModuloDb, Long>, JpaSpecificationExecutor<ModuloDb> {
    List<ModuloDb> findBySectorId(Long sectorId);
}