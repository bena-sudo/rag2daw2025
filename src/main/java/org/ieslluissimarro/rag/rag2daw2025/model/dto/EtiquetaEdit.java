package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 4, max = 30, message = "El nombre de la etiqueta debe contener entre 4 y 30 caracteres.")
    private String nombre;
}
