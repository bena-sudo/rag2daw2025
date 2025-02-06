package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long>{
    Optional<UsuarioDb> findByNickname(String nickname);
    Optional<UsuarioDb> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
