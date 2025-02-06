package org.ieslluissimarro.rag.rag2daw2025.repository;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.micrometer.common.lang.NonNull;


public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long>, JpaSpecificationExecutor<UsuarioDb>{
    Optional<UsuarioDb> findByNickname(String nickname);
    Optional<UsuarioDb> findByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

    @NonNull Page<UsuarioDb> findAll(@NonNull Pageable pageable);
}
