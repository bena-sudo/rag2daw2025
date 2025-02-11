package org.ieslluissimarro.rag.rag2daw2025.security.security.jwt;

import org.ieslluissimarro.rag.rag2daw2025.security.entity.SesionActiva;
import org.ieslluissimarro.rag.rag2daw2025.repository.SesionActivaRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.service.JwtService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    private final SesionActivaRepository sesionActivaRepository;
    
    private static final int INACTIVITY_TIMEOUT_MINUTES = 10;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService,
                                    SesionActivaRepository sesionActivaRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.sesionActivaRepository = sesionActivaRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = req.getHeader("Authorization");
        final String jwt;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(req, res);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            email = jwtService.getEmailUsuarioFromToken(jwt);

            Optional<SesionActiva> sesionOpt = sesionActivaRepository.findByTokenSesion(jwt);
            if (sesionOpt.isEmpty()) {
                filterChain.doFilter(req, res);
                return;
            }
            
            SesionActiva sesion = sesionOpt.get();
            if (sesion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                sesionActivaRepository.delete(sesion);
                filterChain.doFilter(req, res);
                return;
            }

            // Actualizar última actividad
            sesion.setFechaExpiracion(LocalDateTime.now().plusMinutes(INACTIVITY_TIMEOUT_MINUTES));
            sesionActivaRepository.save(sesion);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.error("Token inválido");
        } catch (ExpiredJwtException e) {
            sesionActivaRepository.deleteByTokenSesion(jwt);
        }

        filterChain.doFilter(req, res);
    }
}
