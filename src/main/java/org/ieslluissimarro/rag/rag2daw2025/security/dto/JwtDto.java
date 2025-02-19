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
    private String refreshToken;
    private String bearer = "Bearer";
    private String email;
    private Long idUser;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String refreshToken, String email, Long idUser, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.email = email;
        this.authorities = authorities;
        this.idUser = idUser;
    }
}
