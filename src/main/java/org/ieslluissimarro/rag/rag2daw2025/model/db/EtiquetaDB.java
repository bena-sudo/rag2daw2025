package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "etiquetas")
public class EtiquetaDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único de la etiqueta")
    private Long id;

    @Size(min = 2, message = "El nombre debe tener un tamaño mínimo de 2 caracteres")
    @Size(max = 50, message = "El nombre debe tener un tamaño máximo de 255 caracteres")
    @Schema(example = "DOCUMENTOS", description = "Nombre del fichero entre 2 y 50 caracteres")
    private String nombre;
}
