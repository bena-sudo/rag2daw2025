package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.PermisoNombre;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "permisos")
public class PermisoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 100)
    private PermisoNombre nombre;

    @Column
    private String descripcion;

    // @ManyToOne
    // @JoinColumn(name = "id_categoria")
    // private CategoriaDb categoria;
}