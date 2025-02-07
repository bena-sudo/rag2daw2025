package org.ieslluissimarro.rag.rag2daw2025.model.dto;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permisos")
class PermisoEdit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaDTO categoria;
    
    @ManyToMany(mappedBy = "permisos")
    private List<RolEdit> roles;
    
    @ManyToMany(mappedBy = "permisos")
    private List<UsuarioEdit> usuarios;
}
