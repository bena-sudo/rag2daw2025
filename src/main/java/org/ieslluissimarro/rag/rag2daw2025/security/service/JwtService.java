package org.ieslluissimarro.rag.rag2daw2025.security.service;

import java.util.function.Function;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
 public String generateToken(Authentication authentication);
 public String generateToken(UserDetails userDetails);
 public String generateRefreshToken(UserDetails userDetails);
 public String generateTokenFromUsuario(UsuarioDb usuario);
 public String getEmailUsuarioFromToken(String token);
 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
 public boolean isTokenValid(String token, UserDetails userDetails);
}
