package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "estadistica_documental")
public class EstadisticaDocumentalDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único de la estadística documental")
    private Long id;

    @Column(name = "id_documento")
    @Schema(example = "10", description = "Identificador del documento relacionado")
    private Long idDocumento;

    @Column(name = "id_chunk")
    @Schema(example = "5", description = "Identificador del chunk asociado")
    private Long idChunk;

    @Column(name = "tiempo_revision")
    @Schema(example = "120", description = "Tiempo de revisión en segundos")
    private Integer tiempoRevision;

    @Schema(example = "2023-01-01T12:00:00", description = "Fecha y hora de la estadística")
    private LocalDateTime fecha;

    @Schema(example = "25", description = "Identificador del usuario que realizó la revisión")
    private Long usuario;

    @Size(min = 3, max = 20)
    @Column(name = "estado_final")
    @Schema(example = "APROBADO", description = "Estado final del documento tras la revisión")
    private String estadoFinal;
}
