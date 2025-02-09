package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioEdit {
    
    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String nombre;
    
    @NotBlank
    @Size(max = 255)
    private String nickname;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    
    @Size(max = 15)
    private String telefono;
    
    @NotNull
    private LocalDate fechaNacimiento;
    
    @NotBlank
    @Size(max = 20)
    private String estado;
    
    private Set<RolInfo> roles;
    private Set<Long> permisosIds;
}
