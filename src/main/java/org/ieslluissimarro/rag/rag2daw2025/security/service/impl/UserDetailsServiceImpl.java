package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.security.entity.UsuarioPrincipal;
import org.ieslluissimarro.rag.rag2daw2025.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        //Método que debemos sobreescribir  (debe tener este nombre) de la interfaz UserDetailsService.
        //En nuestro caso buscamos por nickname en la BD y devolvemos un UsuarioPrincipal,
        //que es una implementación de la interfaz UserDetails.
        UsuarioDb usuario = usuarioService.getByNickName(nickname).get();
        return UsuarioPrincipal.build(usuario);
    }
}
