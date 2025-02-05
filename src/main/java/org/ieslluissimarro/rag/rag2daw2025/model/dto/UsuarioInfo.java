package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioInfo {
    private Long id;
    private String nombre;
    private String nickname;
    private String email;
    private Set<RolInfo> roles;
}
