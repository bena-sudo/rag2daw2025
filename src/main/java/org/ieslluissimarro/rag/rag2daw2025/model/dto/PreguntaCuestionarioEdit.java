package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaCuestionarioEdit {
    private Long id;
    private String texto;
    private Long tipoId; 
    private Long cuestionarioId;
    private Long siguienteSiId; 
    private Long siguienteNoId;  
    private boolean finalSi;
    private boolean finalNo;
    private String explicacionSi;
    private String explicacionNo;
    private int orden;
}
