package org.ieslluissimarro.rag.rag2daw2025.security.entity;


import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {//Clase encargarda de generar la seguridad: Implementa los privilegios de cada usuario
    private String nombreCompleto;
    private String nickname;
    private String email;
    private String password;
    private String telefono;
    private LocalDate fechaNacimiento;


    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(String nombreCompleto, String nickname, String email, String password, String telefono, LocalDate fechaNacimiento, Collection<? extends GrantedAuthority> authorities) {
        this.nombreCompleto = nombreCompleto;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.authorities = authorities;
    }

    public static UsuarioPrincipal build(UsuarioDb usuarioDb){ //Convertimos un UsuarioDb es un UsuarioPrincipal con sus privilegios
        List<GrantedAuthority> authorities =
                usuarioDb.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getNombre().name())).collect(Collectors.toList()); //Convertimos los roles de la BD en una lista de 'GrantedAuthority'
        return new UsuarioPrincipal(usuarioDb.getNombre(), usuarioDb.getNickname(), usuarioDb.getEmail(), usuarioDb.getPassword(), usuarioDb.getTelefono(), usuarioDb.getFechaNacimiento(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
