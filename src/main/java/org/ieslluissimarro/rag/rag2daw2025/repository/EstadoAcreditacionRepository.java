package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EstadoAcreditacionDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoAcreditacionRepository extends JpaRepository<EstadoAcreditacionDb, Long>, JpaSpecificationExecutor<EstadoAcreditacionDb> {
}