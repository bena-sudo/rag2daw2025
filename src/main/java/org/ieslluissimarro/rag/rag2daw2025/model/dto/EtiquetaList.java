package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtiquetaList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
}
