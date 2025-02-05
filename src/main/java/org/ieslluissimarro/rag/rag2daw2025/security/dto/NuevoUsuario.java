package org.ieslluissimarro.rag.rag2daw2025.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NuevoUsuario {
    @NotBlank
    private String nickname;
    @NotBlank
    private String nombre;
    @Email
    private String email;
    @NotBlank
    private String password;
    //Al utilizar API Rest utilizamos objetos tipo Json y 
    //es mejor que sean cadenas para agilizar el tráfico
    private Set<String> roles = new HashSet<>();
}