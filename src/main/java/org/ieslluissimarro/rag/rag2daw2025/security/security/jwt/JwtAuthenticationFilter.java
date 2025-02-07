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
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;
  private final SesionActivaRepository sesionActivaRepository;

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

    // Comprueba si la cabecera contiene un token válido
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(req, res);
      return;
    }

    jwt = authHeader.substring(7);

    try {
      email = jwtService.getEmailUsuarioFromToken(jwt);

      // Comprobar si el token está en la tabla de sesiones activas
      Optional<SesionActiva> sesionOpt = sesionActivaRepository.findByTokenSesion(jwt);
      if (sesionOpt.isEmpty()) {
        logger.warn("Intento de uso de un token inválido o eliminado");
        filterChain.doFilter(req, res);
        return;
      }

      // Si el token es válido y no hay autenticación previa, autenticar al usuario
      if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

    } catch (MalformedJwtException e) {
      logger.error("Token mal formado");
    } catch (UnsupportedJwtException e) {
      logger.error("Token no soportado");
    } catch (ExpiredJwtException e) {
      logger.error("Token expirado");
      sesionActivaRepository.deleteByTokenSesion(jwt); // Eliminar sesión si el token ha expirado
    } catch (IllegalArgumentException e) {
      logger.error("Token vacío");
    } catch (SecurityException e) {
      logger.error("Fallo en la firma");
    }

    filterChain.doFilter(req, res);
  }
}














/*import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ieslluissimarro.rag.rag2daw2025.security.service.JwtService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.impl.UserDetailsServiceImpl;
import org.springframework.lang.NonNull;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  // Se ejecutará en cada petición de la API Rest (por heredar de OncePerRequestFilter)
  // y comprobará que sea válido el token utilizando el provider

  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = req.getHeader("Authorization");
    final String jwt;
    final String nickname;
    // Comprueba cabecera
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(req, res);
      return;
    }
    jwt = authHeader.substring(7);
    try { //Hay token y lo procesamos
      nickname = jwtService.getEmailUsuarioFromToken(jwt);
      // Comprueba si el token es valido para permitir el acceso al recurso
      if (nickname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        if (jwtService.isTokenValid(jwt, userDetails)) {// token valido
          // Obtenemos el UserNamePasswordAuthenticationToken en base al userDetails y sus
          // autorizaciones
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
          SecurityContextHolder.getContext().setAuthentication(authToken);// aplicamos autorización al contexto
        }
      }
    } catch (MalformedJwtException e) {
      logger.error("Token mal formado");
    } catch (UnsupportedJwtException e) {
      logger.error("Token no soportado");
    } catch (ExpiredJwtException e) {
      logger.error("Token expirado");
    } catch (IllegalArgumentException e) {
      logger.error("Token vacío");
    } catch (SecurityException e) {
      logger.error("Fallo en la firma");
    }

    filterChain.doFilter(req, res);
  }
}
*/