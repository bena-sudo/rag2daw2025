package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.DocumentoDbDocu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentoRepositoryDocu extends JpaRepository<DocumentoDbDocu, Long>, JpaSpecificationExecutor<DocumentoDbDocu>{
    


}
