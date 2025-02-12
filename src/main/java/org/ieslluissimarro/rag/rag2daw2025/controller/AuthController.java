package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.Mensaje;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.BloqueoCuentaRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.SesionActivaRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.RefreshTokenRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.dto.JwtDto;
import org.ieslluissimarro.rag.rag2daw2025.security.dto.NuevoUsuario;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.SesionActiva;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.RefreshToken;
import org.ieslluissimarro.rag.rag2daw2025.security.service.JwtService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.RolDeteilsService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.UsuarioService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolDeteilsService rolService;

    @Autowired
    JwtService jwtProvider;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    SesionActivaRepository sesionActivaRepository;

    @Autowired
    BloqueoCuentaRepository bloqueoCuentaRepository;
    
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    /**
     * Registro de un nuevo usuario
     */
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos o email inválido"));
        }
        if (usuarioService.existsByNickname(nuevoUsuario.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El nickname del usuario ya existe"));
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El email del usuario ya existe"));
        }

        //LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ? nuevoUsuario.getFechaNacimiento() : LocalDate.now();
        // Manejo de fecha de nacimiento si no se proporciona
        //LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ? nuevoUsuario.getFechaNacimiento() : LocalDate.now();

        UsuarioDb usuarioDb = new UsuarioDb(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getNickname(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()),
            nuevoUsuario.getTelefono()
            //fechaNacimiento
        );

        Set<RolDb> rolesDb = new HashSet<>();
        Optional<RolDb> rol = rolService.getByRolNombre(RolNombre.USUARIO);
        rolesDb.add(rol.orElseThrow(() -> new RuntimeException("Rol no encontrado")));

        usuarioDb.setRoles(rolesDb);
        usuarioService.save(usuarioDb);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario creado"));
    }

    /**
     * Login de usuario (Devuelve Access Token y Refresh Token)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos"));


            Optional<UsuarioDb> usuarioOpt = usuarioService.getByEmail(loginUsuario.getEmail());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Usuario no encontrado"));
            }
    
            UsuarioDb usuario = usuarioOpt.get();
            Optional<BloqueoCuentaDb> bloqueoCuentaOpt = bloqueoCuentaRepository.findByUsuarioId(usuario.getId());
            if (bloqueoCuentaOpt.isPresent() && bloqueoCuentaOpt.get().isBloqueado()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Mensaje("Cuenta bloqueada por múltiples intentos fallidos"));
            }


            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtProvider.generateToken(authentication);
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
                JwtDto jwtDto = new JwtDto(jwt,"Bearer", userDetails.getUsername(), userDetails.getAuthorities());
    
                // Crear sesión activa con fecha de expiración
            SesionActiva sesion = new SesionActiva(usuario, jwt);
            sesion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10)); // Expira en 30 minutos
            sesionActivaRepository.save(sesion);
                
               
    
                // Resetear intentos fallidos en caso de éxito
                usuarioService.resetearIntentosFallidos(loginUsuario.getEmail());
    
                return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
            } catch (Exception e) {
                // Incrementar intentos fallidos en caso de error
                usuarioService.incrementarIntentosFallidos(loginUsuario.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Credenciales incorrectas"));
            }



            /*
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
    
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
        JwtDto jwtDto = new JwtDto(jwt, "Bearer", userDetails.getUsername(), userDetails.getAuthorities());
    
        UsuarioDb usuario = usuarioService.getByEmail(loginUsuario.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Crear sesión activa con fecha de expiración
        SesionActiva sesion = new SesionActiva(usuario, jwt);
        sesion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10)); // Expira en 30 minutos
        sesionActivaRepository.save(sesion);
    
        return ResponseEntity.status(HttpStatus.OK).body(jwtDto);

        */
    }

    /**
     * Endpoint para refrescar el Access Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
    if (refreshToken.startsWith("Bearer ")) {
        refreshToken = refreshToken.substring(7);
    }

    Optional<String> emailOpt = refreshTokenService.refreshAccessToken(refreshToken);

    if (emailOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Refresh Token inválido o expirado"));
    }

    String email = emailOpt.get();
    UsuarioDb usuario = usuarioService.getByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    String newAccessToken = jwtProvider.generateTokenFromUsuario(usuario);
    RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(usuario);

    // Convertir Set<RolDb> a Collection<? extends GrantedAuthority>
    List<GrantedAuthority> authorities = usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getNombre().name()))
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtDto(newAccessToken, newRefreshToken.getToken(), usuario.getEmail(), authorities));
}



    /**
     * Logout de usuario (Elimina Access y Refresh Token)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        logger.info("Token recibido en logout: " + token);

        Optional<SesionActiva> sesion = sesionActivaRepository.findByTokenSesion(token);
        if (sesion.isEmpty()) {
            logger.warn("Token inválido o sesión no encontrada");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Token inválido o sesión no encontrada"));
        }

        UsuarioDb usuario = sesion.get().getUsuario();
        logger.info("Usuario encontrado: " + usuario.getEmail());

        try {
            sesionActivaRepository.deleteByUsuarioId(usuario.getId());
            refreshTokenRepository.deleteByUsuario(usuario); // Elimina el Refresh Token del usuario
            logger.info("Todas las sesiones y tokens del usuario eliminados");
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Todas las sesiones del usuario han sido cerradas correctamente"));
        } catch (Exception e) {
            logger.error("Error al eliminar sesiones del usuario: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensaje("Error al cerrar sesión"));
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/desbloquear/{usuarioId}")
    public ResponseEntity<?> desbloquearCuenta(@PathVariable Long usuarioId) {
        try {
            usuarioService.desbloquearCuenta(usuarioId);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Cuenta desbloqueada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensaje("Error al desbloquear la cuenta"));
        }
    }
    
    
}
