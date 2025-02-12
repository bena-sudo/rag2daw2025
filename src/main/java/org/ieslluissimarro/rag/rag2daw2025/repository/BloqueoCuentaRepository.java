package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueoCuentaRepository extends JpaRepository<BloqueoCuentaDb, Long> {
    Optional<BloqueoCuentaDb> findByUsuarioId(Long usuarioId);
}