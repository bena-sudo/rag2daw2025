package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.ieslluissimarro.rag.rag2daw2025.repository.RefreshTokenRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.RefreshToken;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(UsuarioDb usuario) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsuario(usuario);
        refreshToken.setToken(java.util.UUID.randomUUID().toString());
        refreshToken.setExpiracion(Instant.now().plusSeconds(604800)); // 7 días
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<String> refreshAccessToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .filter(token -> token.getExpiracion().isAfter(Instant.now()))
                .map(token -> token.getUsuario().getEmail());
    }

    public void deleteByUsuario(UsuarioDb usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);
    }
    public RefreshToken rotateRefreshToken(UsuarioDb usuario) {
        refreshTokenRepository.deleteByUsuario(usuario); // Elimina el Refresh Token viejo
        return createRefreshToken(usuario); // Genera uno nuevo
    }
}
