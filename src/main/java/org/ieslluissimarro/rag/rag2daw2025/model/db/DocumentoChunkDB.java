package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoChunk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "documentos_chunks")
public class DocumentoChunkDB implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único del chunk")
    private Long id;

    @NotNull(message = "El id del documento no puede ser nulo.")
    @Column(name = "id_documento")
    @Schema(example = "10", description = "Identificador del documento al que pertenece el chunk")
    private Long idDocumento;

    @Column(name = "id_doc_rag")
    @Schema(example = "100", description = "Identificador del documento en el sistema RAG (opcional)")
    private Integer idDocRag;

    @NotNull(message = "El orden del chunk no puede ser nulo.")
    @Column(name = "chunk_order")
    @Schema(example = "1", description = "Orden del chunk dentro del documento")
    private Integer chunkOrder;

    @NotNull(message = "El texto del chunk no puede estar vacío.")
    @Column(name = "chunk_text", columnDefinition = "TEXT")
    @Schema(example = "Este es el contenido del chunk", description = "Texto o contenido del chunk")
    private String chunkText;

    @Column(name = "chunked_by")
    @Schema(example = "5", description = "Identificador del usuario o proceso que realizó el chunk")
    private Long chunkedBy;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Schema(example = "APROBADO", description = "Estado del chunk")
    private EstadoChunk estado;

    @Column(name = "fecha_creacion", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Schema(example = "2023-01-01T12:00:00", description = "Fecha y hora de creación del registro")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Schema(example = "2023-01-02T12:00:00", description = "Fecha y hora de la última modificación del registro")
    private LocalDateTime fechaModificacion;
}
