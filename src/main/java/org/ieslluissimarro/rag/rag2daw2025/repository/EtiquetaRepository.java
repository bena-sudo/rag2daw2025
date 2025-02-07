package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface EtiquetaRepository extends JpaRepository<EtiquetaDB, Long>, JpaSpecificationExecutor<EtiquetaDB> {
    @NonNull
    Page<EtiquetaDB> findAll(@NonNull Pageable pageable);
}
