package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.BloqueoCuentaList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueoCuentaRepository extends JpaRepository<BloqueoCuentaDb, Long> {
    Optional<BloqueoCuentaDb> findByUsuarioId(Long usuarioId);


   @Query("SELECT new org.ieslluissimarro.rag.rag2daw2025.model.dto.BloqueoCuentaList(" +
       "b.id, b.usuarioId, u.email, b.intentosFallidos, b.bloqueado, b.fechaBloqueo) " +
       "FROM BloqueoCuentaDb b JOIN UsuarioDb u ON b.usuarioId = u.id")
    List<BloqueoCuentaList> findAllWithEmail();


}