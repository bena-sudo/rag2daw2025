package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MensajeEdit {
    private Long id;
    private Long acreditacion_id;
    private Long usuario_id;
    private String contenido;
    private LocalDateTime fecha = LocalDateTime.now();
}