package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditoriaEventoList {

    private Long id;
    private Long usuarioId;
    private String tipoEvento;
    private String tablaAfectada;
    private String datoAnterior;
    private String datoNuevo;
    private String descripcion;
    private LocalDateTime fecha;
}