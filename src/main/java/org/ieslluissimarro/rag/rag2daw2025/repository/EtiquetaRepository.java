package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EtiquetaRepository extends JpaRepository<EtiquetaDB, Long>, JpaSpecificationExecutor<EtiquetaDB> {

}
