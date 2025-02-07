package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.security.entity.SesionActiva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SesionActivaRepository extends JpaRepository<SesionActiva, Long> {
    Optional<SesionActiva> findByTokenSesion(String token);
    void deleteByTokenSesion(String token);
    void deleteByUsuarioId(Long usuarioId);
}
