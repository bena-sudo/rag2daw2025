package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.LoginUsuario;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.Mensaje;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.SesionActivaRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.dto.JwtDto;
import org.ieslluissimarro.rag.rag2daw2025.security.dto.NuevoUsuario;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.SesionActiva;
import org.ieslluissimarro.rag.rag2daw2025.security.service.JwtService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.RolService;
import org.ieslluissimarro.rag.rag2daw2025.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtService jwtProvider;

    @Autowired
    SesionActivaRepository sesionActivaRepository;

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

        // Manejo de fecha de nacimiento si no se proporciona
        LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ? nuevoUsuario.getFechaNacimiento() : LocalDate.now();

        // Creación del usuario
        UsuarioDb usuarioDb = new UsuarioDb(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getNickname(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()),
            nuevoUsuario.getTelefono(),
            fechaNacimiento
        );

        // Asignar automáticamente el rol de USUARIO
        Set<RolDb> rolesDb = new HashSet<>();
        Optional<RolDb> rol = rolService.getByRolNombre(RolNombre.USUARIO);
        rolesDb.add(rol.orElseThrow(() -> new RuntimeException("Rol no encontrado")));

        usuarioDb.setRoles(rolesDb);
        usuarioService.save(usuarioDb);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario creado"));
    }

    /**
     * Login de usuario
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        // Guardar la sesión activa en la BD
        UsuarioDb usuario = usuarioService.getByEmail(loginUsuario.getEmail()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        SesionActiva sesion = new SesionActiva(usuario, jwt);
        sesionActivaRepository.save(sesion);

        return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
    }

    /**
     * Logout de usuario
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Eliminar el prefijo "Bearer "
        }

        Optional<SesionActiva> sesion = sesionActivaRepository.findByTokenSesion(token);
        if (sesion.isPresent()) {
            sesionActivaRepository.deleteByTokenSesion(token);
            return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Sesión cerrada correctamente"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Token inválido o sesión no encontrada"));
    }
    
    
    
    
    /* 
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtService jwtProvider;

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

        LocalDate fechaNacimiento = nuevoUsuario.getFechaNacimiento() != null ? nuevoUsuario.getFechaNacimiento() : LocalDate.now();

        UsuarioDb usuarioDb = new UsuarioDb(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getNickname(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()),
            nuevoUsuario.getTelefono(),
            fechaNacimiento
        );
        
         // Asignar automáticamente el rol de USUARIO
        Set<RolDb> rolesDb = new HashSet<>();
        Optional<RolDb> rol = rolService.getByRolNombre(RolNombre.USUARIO);
        rolesDb.add(rol.orElseThrow(() -> new RuntimeException("Rol no encontrado")));

        usuarioDb.setRoles(rolesDb);
        usuarioService.save(usuarioDb);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario creado"));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Datos incorrectos"));
        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return ResponseEntity.status(HttpStatus.OK).body(jwtDto);
    }
        */
        
}
