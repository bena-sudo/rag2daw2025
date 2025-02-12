package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.ieslluissimarro.rag.rag2daw2025.model.db.BloqueoCuentaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.BloqueoCuentaRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
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
        usuarioRepository.save(usuario);
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
        
        bloqueoCuenta.setIntentosFallidos(bloqueoCuenta.getIntentosFallidos() + 1);
        if (bloqueoCuenta.getIntentosFallidos() >= 5) {
            bloqueoCuenta.setBloqueado(true);
            bloqueoCuenta.setFechaBloqueo(LocalDateTime.now());
        }
        bloqueoCuentaRepository.save(bloqueoCuenta);
    }

    @Transactional
    public void resetearIntentosFallidos(String email) {
        UsuarioDb usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        BloqueoCuentaDb bloqueoCuenta = bloqueoCuentaRepository.findByUsuarioId(usuario.getId())
            .orElse(new BloqueoCuentaDb(usuario.getId(), 0, false, null));
        
        bloqueoCuenta.setIntentosFallidos(0);
        bloqueoCuenta.setBloqueado(false);
        bloqueoCuenta.setFechaBloqueo(null);
        bloqueoCuentaRepository.save(bloqueoCuenta);
    }


    @Transactional
    public void desbloquearCuenta(Long usuarioId) {
        BloqueoCuentaDb bloqueoCuenta = bloqueoCuentaRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        bloqueoCuenta.setIntentosFallidos(0);
        bloqueoCuenta.setBloqueado(false);
        bloqueoCuenta.setFechaBloqueo(null);
        bloqueoCuentaRepository.save(bloqueoCuenta);
    }



}