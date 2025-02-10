package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.UsuarioPrincipal;
import org.ieslluissimarro.rag.rag2daw2025.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UsuarioDb> usuarioOpt = usuarioService.getByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        UsuarioDb usuario = usuarioOpt.get();

        // DEBUG: Verificar si los roles del usuario están cargados correctamente
        System.out.println("Usuario encontrado: " + usuario.getEmail());
        System.out.println("Roles del usuario: " + usuario.getRoles());

        return UsuarioPrincipal.build(usuario);
    }
}
