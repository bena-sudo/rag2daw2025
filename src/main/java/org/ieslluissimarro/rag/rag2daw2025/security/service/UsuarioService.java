package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.BloqueoCuentaRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.AuditoriaEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private AuditoriaEventoService auditoriaEventoService;

    @Autowired
    private BloqueoCuentaRepository bloqueoCuentaRepository;


                               
    public Optional<UsuarioDb> getByNickName(String nickname){
        return usuarioRepository.findByNickname(nickname);
    }

    public Optional<UsuarioDb> getByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public boolean existsByNickname(String nickname){
        return usuarioRepository.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(@NonNull UsuarioDb usuario){
        boolean isNew = usuario.getId() == null;
        usuarioRepository.save(usuario);

        // Registrar evento de auditoría
        auditoriaEventoService.registrarEvento(
            usuario.getId(),
            isNew ? "creacion" : "modificacion",
            "usuarios",
            null,
            usuario.getEmail(),
            isNew ? "Creación de usuario" : "Modificación de usuario"
        );
    }



    @Transactional
    public void incrementarIntentosFallidos(String email) {
        UsuarioDb usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el usuari te el rol administrador per a no incrementar
        boolean esAdministrador = usuario.getRoles().stream()
            .anyMatch(rol -> rol.getNombre().equals(RolNombre.ADMINISTRADOR));

        if (esAdministrador) {
            return;
        }

        BloqueoCuentaDb bloqueoCuenta = bloqueoCuentaRepository.findByUsuarioId(usuario.getId())
            .orElse(new BloqueoCuentaDb(usuario.getId(), 0, false, null));
        

        int intentosAnteriores = bloqueoCuenta.getIntentosFallidos();
        bloqueoCuenta.setIntentosFallidos(intentosAnteriores + 1);
        if (bloqueoCuenta.getIntentosFallidos() >= 5) {
            bloqueoCuenta.setBloqueado(true);
            bloqueoCuenta.setFechaBloqueo(LocalDateTime.now());
        }
        bloqueoCuentaRepository.save(bloqueoCuenta);


         // Registrar evento de auditoría
         auditoriaEventoService.registrarEvento(
            usuario.getId(),
            "modificacion",
            "bloqueo_cuentas",
            "intentos_fallidos: " + intentosAnteriores,
            "intentos_fallidos: " + bloqueoCuenta.getIntentosFallidos(),
            "Incremento de intentos fallidos de inicio de sesión"
        );
    }

    @Transactional
    public void resetearIntentosFallidos(String email) {
        UsuarioDb usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        BloqueoCuentaDb bloqueoCuenta = bloqueoCuentaRepository.findByUsuarioId(usuario.getId())
            .orElse(new BloqueoCuentaDb(usuario.getId(), 0, false, null));
        
        int intentosAnteriores = bloqueoCuenta.getIntentosFallidos();
        bloqueoCuenta.setIntentosFallidos(0);
        bloqueoCuenta.setBloqueado(false);
        bloqueoCuenta.setFechaBloqueo(null);
        bloqueoCuentaRepository.save(bloqueoCuenta);

        // Registrar evento de auditoría
        auditoriaEventoService.registrarEvento(
            usuario.getId(),
            "modificacion",
            "bloqueo_cuentas",
            "intentos_fallidos: " + intentosAnteriores,
            "intentos_fallidos: 0",
            "Reseteo de intentos fallidos de inicio de sesión"
        );
    }


    @Transactional
    public void desbloquearCuenta(Long usuarioId) {
        BloqueoCuentaDb bloqueoCuenta = bloqueoCuentaRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        int intentosAnteriores = bloqueoCuenta.getIntentosFallidos();
        bloqueoCuenta.setIntentosFallidos(0);
        bloqueoCuenta.setBloqueado(false);
        bloqueoCuenta.setFechaBloqueo(null);
        bloqueoCuentaRepository.save(bloqueoCuenta);


        // Registrar evento de auditoría
        auditoriaEventoService.registrarEvento(
            usuarioId,
            "modificacion",
            "bloqueo_cuentas",
            "intentos_fallidos: " + intentosAnteriores,
            "intentos_fallidos: 0",
            "Desbloqueo de cuenta de usuario"
        );
    }



}