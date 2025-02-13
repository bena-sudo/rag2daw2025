package org.ieslluissimarro.rag.rag2daw2025.repository;


import org.ieslluissimarro.rag.rag2daw2025.security.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
