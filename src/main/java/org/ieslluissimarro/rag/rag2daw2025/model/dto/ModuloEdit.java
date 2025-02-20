package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModuloEdit {
    private Long id;
    private String nombre;
    private Integer nivel;
    private Integer sector_id;
}
