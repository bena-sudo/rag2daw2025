package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.MensajeDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeDb, Long>, JpaSpecificationExecutor<MensajeDb> {
    List<MensajeDb> findByUsuarioId(Long usuarioId);
    List<MensajeDb> findByAcreditacionId(Long acreditacionId);
}
