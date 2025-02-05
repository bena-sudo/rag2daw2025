package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadisticaList {
    private Long id;
    private Long idDocumento;
    private Long idChunk;
    private Integer tiempoRevision;
    private LocalDateTime fecha;
    private Long usuario;
    private String estadoFinal;
}
