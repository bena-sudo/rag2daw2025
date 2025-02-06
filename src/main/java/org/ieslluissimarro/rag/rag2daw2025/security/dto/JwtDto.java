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
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;
    //Constructor sin el atributo 'bearer'
    public JwtDto(String token, String nickname, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nickname = nickname;
        this.authorities = authorities;
    }
}
