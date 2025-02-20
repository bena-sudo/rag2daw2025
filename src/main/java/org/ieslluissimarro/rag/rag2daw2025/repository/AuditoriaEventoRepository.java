package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuditoriaEventoRepository extends JpaRepository<AuditoriaEventoDb, Long> {
    List<AuditoriaEventoDb> findByUsuarioId(Long usuarioId);
}
