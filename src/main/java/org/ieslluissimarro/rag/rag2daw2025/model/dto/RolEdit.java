package org.ieslluissimarro.rag.rag2daw2025.model.dto;


import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
class RolEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    
    @ManyToMany(mappedBy = "roles")
    private List<UsuarioEdit> usuarios;
    
    @ManyToMany
    @JoinTable(
        name = "rol_permisos",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private List<PermisoEdit> permisos;
}
