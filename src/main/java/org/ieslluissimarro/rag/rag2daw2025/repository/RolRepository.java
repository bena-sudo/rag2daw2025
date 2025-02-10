package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolRepository extends JpaRepository<RolDb, Long>{
    Optional<RolDb> findByNombre(RolNombre rolNombre);

    @Query("SELECT u FROM UsuarioDb u JOIN u.roles r WHERE r.nombre = :rolNombre")
    List<UsuarioDb> findUsuariosByRolNombre(RolNombre rolNombre);
}
