package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentoRepositoryDocu extends JpaRepository<DocumentoDB, Long>, JpaSpecificationExecutor<DocumentoDB>{
    


}
