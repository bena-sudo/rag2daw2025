package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import io.jsonwebtoken.*;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.UsuarioPrincipal;
import org.ieslluissimarro.rag.rag2daw2025.security.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService{ // Se encargará de generar el token y comprobar su validez

    @Value("${security.jwt.secret-key}")// valor en application.properties
    private String secret;
    @Value("${security.jwt.expiration-time}")// valor en application.properties
    private int expiration;
    @Value("${security.jwt.refresh-expiration-time}")// valor en application.properties
    private int refreshExpirationTime;
    
    @Override
    public String generateToken(Authentication authentication) { // genera el token
        // Obtenemos el usuario principal (UserDetails)
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        // Configuramos nickname (getUsername), fecha de expedición, fecha de expiración
        // y firmamos
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getEmailUsuarioFromToken(String token) {// extrae el nickname del token
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = getEmailUsuarioFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    @Override
    public String generateTokenFromUsuario(UsuarioDb usuario) {
    return Jwts.builder()
            .setSubject(usuario.getEmail()) // El sub es el email
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}
    
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //Extraer el usuario del token

    @Value("${security.jwt.secret-key}") // valor en application.properties
    private static String SECRET_KEY;

    private static Claims extractClaim(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(SECRET_KEY)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    public static String extractEmail(String token) {
        return extractClaim(token).getSubject();
    }

}
