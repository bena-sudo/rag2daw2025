package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioEdit {
    
    @Schema(example = "12345", description = "ID único del usuario")
    private Long id;

    @NotBlank
    @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
    @Schema(example = "Juan Pérez", description = "Nombre completo del usuario")
    private String nombre;
    
    @NotBlank
    @Size(max = 255, message = "El nickname no puede tener más de 255 caracteres")
    @Schema(example = "ejemplo2025", description = "Nickname único del usuario")
    private String nickname;

    @NotBlank
    @Email
    @Size(max = 255, message = "El email no puede tener más de 255 caracteres")
    @Schema(example = "ejemplo@example.com", description = "Correo electrónico del usuario")
    private String email;
    
    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "El teléfono solo puede contener números y opcionalmente un prefijo de país (+)")
    @Schema(example = "+34123456789", description = "Número de teléfono del usuario (con o sin prefijo de país)")
    private String telefono;
    
    /*
    @NotNull
    @Schema(example = "1990-04-25", description = "Fecha de nacimiento del usuario (formato yyyy-MM-dd)")
    private LocalDate fechaNacimiento;
    */
    
    @Size(max = 20, message = "El estado no puede tener más de 20 caracteres")
    @Schema(example = "Activo", description = "Estado del usuario (ej. Activo, Inactivo)")
    private String estado;
    
    @Schema(description = "IDs de los roles asociados al usuario")
    private Set<Long> roleIds;
    
    @Schema(description = "IDs de los permisos asociados al usuario")
    private Set<Long> permisosIds;
}
