package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentChunkDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentoChunkEditRepository extends JpaRepository<DocumentChunkDB, Long>,JpaSpecificationExecutor<DocumentChunkDB>{

    
}
