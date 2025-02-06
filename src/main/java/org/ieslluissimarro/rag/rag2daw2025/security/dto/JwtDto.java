package org.ieslluissimarro.rag.rag2daw2025.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    //Constructor sin el atributo 'bearer'
    public JwtDto(String token, String email, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.email = email;
        this.authorities = authorities;
    }
}
