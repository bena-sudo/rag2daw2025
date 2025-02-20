package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespuestaEdit {
    private Long id;
    private String respuesta;
    private Integer pregunta_id;
    private Integer usuario_id;
}