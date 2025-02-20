package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.security.entity.SesionActiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SesionActivaRepository extends JpaRepository<SesionActiva, Long> {
    
    Optional<SesionActiva> findByTokenSesion(String token);

    void deleteByTokenSesion(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM SesionActiva s WHERE s.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Encontrar sesiones expiradas
    List<SesionActiva> findByFechaExpiracionBefore(LocalDateTime fecha);

    boolean existsByUsuarioId(Long usuarioId);


}
