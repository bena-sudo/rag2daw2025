package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadoAcreditacionEdit {
    private Long id;
    private String estado;
    private LocalDateTime fecha_actualizacion = LocalDateTime.now();
    private Long usuario_id;
    private Long modulo_id;
    private Long asesor_id;
}

