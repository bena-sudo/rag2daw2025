package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.BloqueoCuentaList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.Mensaje;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.SesionActivaResponse;
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
import org.ieslluissimarro.rag.rag2daw2025.srv.BloqueoCuentaService;
import org.ieslluissimarro.rag.rag2daw2025.srv.EmailService;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.AuditoriaEventoServiceImpl;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Autowired
    private AuditoriaEventoServiceImpl auditoriaEventoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BloqueoCuentaService bloqueoCuentaService;

    /**
     * Registro de un nuevo usuario
     */
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario y envía un correo de confirmación.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o usuario ya existe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)))
    })
    
    @CrossOrigin(origins = "http://13.73.226.200:80")
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Mensaje("Datos incorrectos o email inválido"));
        }
        if (usuarioService.existsByNickname(nuevoUsuario.getNickname())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El nickname del usuario ya existe"));
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El email del usuario ya existe"));
        }

        // LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ?
        // nuevoUsuario.getFechaNacimiento() : LocalDate.now();
        // Manejo de fecha de nacimiento si no se proporciona
        // LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ?
        // nuevoUsuario.getFechaNacimiento() : LocalDate.now();

        UsuarioDb usuarioDb = new UsuarioDb(
                nuevoUsuario.getNombre(),
                nuevoUsuario.getNickname(),
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()),
                nuevoUsuario.getTelefono()
        // fechaNacimiento
        );

        usuarioDb.setEstado("pendiente");

        Set<RolDb> rolesDb = new HashSet<>();
        Optional<RolDb> rol = rolService.getByRolNombre(RolNombre.USUARIO);
        rolesDb.add(rol.orElseThrow(() -> new RuntimeException("Rol no encontrado")));

        usuarioDb.setRoles(rolesDb);
        usuarioService.save(usuarioDb);

        // Enviar correo de confirmación
        String token = jwtProvider.generateTokenFromUsuario(usuarioDb);
        String confirmUrl = "http://localhost:8090/auth/confirmar?token=" + token;
        String mensaje = "Por favor, confirma tu cuenta haciendo clic en el siguiente enlace: " + confirmUrl;
        emailService.sendEmail(nuevoUsuario.getEmail(), "Confirmación de cuenta", mensaje);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Mensaje("Usuario creado. Por favor, confirma tu cuenta a través del correo electrónico enviado."));
    }

    /**
     * Login de usuario
     */
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un JWT para acceder a la API.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "403", description = "Cuenta bloqueada"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos"));

        Optional<UsuarioDb> usuarioOpt = usuarioService.getByEmail(loginUsuario.getEmail());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Usuario no encontrado"));
        }

        UsuarioDb usuario = usuarioOpt.get();
        if ("pendiente".equals(usuario.getEstado())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Mensaje("Debe confirmar su cuenta a través del correo electrónico enviado"));
        }
        usuarioService.verificarBloqueo(usuario.getId());
        Optional<BloqueoCuentaDb> bloqueoCuentaOpt = bloqueoCuentaRepository.findByUsuarioId(usuario.getId());
        if (bloqueoCuentaOpt.isPresent() && bloqueoCuentaOpt.get().isBloqueado()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("Cuenta bloqueada por múltiples intentos fallidos"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            JwtDto jwtDto = new JwtDto(jwt, "Bearer", userDetails.getUsername(),usuario.getId(), userDetails.getAuthorities());

            // Crear sesión activa con fecha de expiración
            SesionActiva sesion = new SesionActiva(usuario, jwt);
            sesion.setFechaExpiracion(LocalDateTime.now().plusMinutes(300)); // Expira en 30 minutos
            sesionActivaRepository.save(sesion);

            // Resetear intentos fallidos en caso de éxito
            usuarioService.resetearIntentosFallidos(loginUsuario.getEmail());

            // Registrar evento de auditoría
            auditoriaEventoService.registrarEvento(
                    usuario.getId(),
                    "login",
                    "usuarios",
                    null,
                    loginUsuario.getEmail(),
                    "Inicio de sesión exitoso");

            usuarioService.activarUsuario(loginUsuario.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
        } catch (Exception e) {
            // Incrementar intentos fallidos en caso de error
            usuarioService.incrementarIntentosFallidos(loginUsuario.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Credenciales incorrectas"));
        }
    }

    /*
     * Authentication authentication = authenticationManager.authenticate(
     * new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(),
     * loginUsuario.getPassword()));
     * 
     * SecurityContextHolder.getContext().setAuthentication(authentication);
     * String jwt = jwtProvider.generateToken(authentication);
     * UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     * 
     * JwtDto jwtDto = new JwtDto(jwt, "Bearer", userDetails.getUsername(),
     * userDetails.getAuthorities());
     * 
     * UsuarioDb usuario = usuarioService.getByEmail(loginUsuario.getEmail())
     * .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
     * 
     * // Crear sesión activa con fecha de expiración
     * SesionActiva sesion = new SesionActiva(usuario, jwt);
     * sesion.setFechaExpiracion(LocalDateTime.now().plusMinutes(10)); // Expira en
     * 30 minutos
     * sesionActivaRepository.save(sesion);
     * 
     * return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
     * 
     */

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Mensaje("Refresh Token inválido o expirado"));
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

        return ResponseEntity
                .ok(new JwtDto(newAccessToken, newRefreshToken.getToken(), usuario.getEmail(),usuario.getId(), authorities));
    }

    /**
     * Logout de usuario (Elimina Access y Refresh Token)
     */
    @Operation(summary = "Cerrar sesión del usuario", description = "Cierra la sesión activa de un usuario eliminando su token JWT y Refresh Token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión cerrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido o sesión no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al cerrar sesión", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)))
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        logger.info("Token recibido en logout: " + token);

        Optional<SesionActiva> sesion = sesionActivaRepository.findByTokenSesion(token);
        if (sesion.isEmpty()) {
            logger.warn("Token inválido o sesión no encontrada");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Mensaje("Token inválido o sesión no encontrada"));
        }

        UsuarioDb usuario = sesion.get().getUsuario();
        logger.info("Usuario encontrado: " + usuario.getEmail());

        try {

            usuarioService.inactivarUsuario(usuario.getEmail());

            sesionActivaRepository.deleteByUsuarioId(usuario.getId());
            refreshTokenRepository.deleteByUsuario(usuario); // Elimina el Refresh Token del usuario
            logger.info("Todas las sesiones y tokens del usuario eliminados");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Mensaje("Todas las sesiones del usuario han sido cerradas correctamente"));
        } catch (Exception e) {
            logger.error("Error al eliminar sesiones del usuario: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensaje("Error al cerrar sesión"));
        }
    }

    /**
     * Desbloquear una cuenta de usuario
     */
    @Operation(summary = "Desbloquear cuenta de usuario", description = "Permite a un administrador desbloquear una cuenta de usuario bloqueada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta desbloqueada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al desbloquear la cuenta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)))
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_CUENTAS_BLOQUEADAS')")
    @PostMapping("/desbloquear/{usuarioId}")
    public ResponseEntity<?> desbloquearCuenta(@PathVariable Long usuarioId) {
        try {
            usuarioService.desbloquearCuenta(usuarioId);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Cuenta desbloqueada exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Mensaje("Error al desbloquear la cuenta"));
        }
    }

    /**
     * Confirmar cuenta de usuario
     */
    @Operation(summary = "Confirmar cuenta de usuario", description = "Confirma la cuenta de usuario mediante un token enviado al correo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta confirmada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)))
    })
    @GetMapping("/confirmar")
    public ResponseEntity<?> confirmarCuenta(@RequestParam("token") String token) {
        String email = jwtProvider.getEmailUsuarioFromToken(token);
        Optional<UsuarioDb> usuarioOpt = usuarioService.getByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Token inválido"));
        }

        usuarioService.activarCuenta(email);

        return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Cuenta confirmada exitosamente"));
    }

    /**
     * Listar cuentas bloqueadas
     */
    @Operation(summary = "Listar cuentas bloqueadas", description = "Devuelve una lista de cuentas bloqueadas por intentos fallidos de login.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cuentas bloqueadas obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BloqueoCuentaList.class)))
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_CUENTAS_BLOQUEADAS')")
    @GetMapping("/bloqueadas")
    public ResponseEntity<List<BloqueoCuentaList>> listarCuentasBloqueadas() {
        List<BloqueoCuentaList> bloqueos = bloqueoCuentaService.listarCuentasBloqueadas();
        return ResponseEntity.ok(bloqueos);
    }

    /**
     * Cerrar sesión de un usuario por parte del administrador
     */
    @Operation(summary = "Cerrar sesión de un usuario (Administrador)", description = "Permite a un administrador cerrar la sesión de cualquier usuario activo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión del usuario cerrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o sin sesiones activas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)))
    })
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @DeleteMapping("/logout/{usuarioId}")
    public ResponseEntity<?> logoutUsuario(@PathVariable Long usuarioId) {
        Optional<UsuarioDb> usuarioOpt = usuarioService.getUsuarioById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("Usuario no encontrado"));
        }

        boolean tieneSesiones = sesionActivaRepository.existsByUsuarioId(usuarioId);
        if (!tieneSesiones) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Mensaje("El usuario no tiene sesiones activas"));
        }

        sesionActivaRepository.deleteByUsuarioId(usuarioId);

        return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Sesión del usuario cerrada correctamente"));
    }

    /**
     * Obtener sesiones activas
     */
    @Operation(summary = "Obtener sesiones activas", description = "Devuelve una lista con el ID del usuario, su email, nickname y la fecha de activación de la sesión activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de sesiones activas obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SesionActivaResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "No hay sesiones activas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Mensaje.class)) })
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_SESIONES_ACTIVAS')")
    @GetMapping("/sesiones-activas")
    public ResponseEntity<?> getSesionesActivas() {
        List<SesionActivaResponse> sesiones = sesionActivaRepository.findAll().stream()
                .map(sesion -> new SesionActivaResponse(
                        sesion.getUsuario().getId(),
                        sesion.getUsuario().getEmail(),
                        sesion.getUsuario().getNickname(),
                        sesion.getFechaInicio()))
                .collect(Collectors.toList());

        if (sesiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("No hay sesiones activas"));
        }

        return ResponseEntity.ok(sesiones);
    }

}
