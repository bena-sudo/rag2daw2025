package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.ieslluissimarro.rag.rag2daw2025.repository.VerificationTokenRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.VerificationToken;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken createVerificationToken(UsuarioDb usuario) {
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUsuario(usuario);
        token.setExpiryDate(LocalDateTime.now().plusDays(1));
        return verificationTokenRepository.save(token);
    }

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).orElse(null);
    }
}
