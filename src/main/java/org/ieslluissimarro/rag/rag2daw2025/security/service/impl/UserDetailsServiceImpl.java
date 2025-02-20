package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.UsuarioPrincipal;
import org.ieslluissimarro.rag.rag2daw2025.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        UsuarioDb usuario = usuarioService.getByEmail(email).get();
        return UsuarioPrincipal.build(usuario);
    }

    //Obtener el usuaruo autenticado en base al token JWT
    public static UserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    //Para obtener el usuario desde el token JWT
    public static String getCurrentUsernameFromJWT(String token) {
        return JwtServiceImpl.extractEmail(token);
    }



    /*@Override
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
    }*/
}
