package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDateTime;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoChunk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "document_chunk")
public class DocumentChunkEditDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del documento no puede ser nulo")
    private Long idDocumento;

    private Integer idDocRag;

    @NotNull(message = "El orden del chunk no puede ser nulo")
    private Integer chunkOrder;

    @NotNull(message = "El texto del chunk no puede estar vacío")
    @Size(min = 1)
    @Lob
    private String chunkText;

    private String chunkedBy;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'pendiente'")
    private EstadoChunk estado = EstadoChunk.PENDIENTE;

    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private LocalDateTime fechaModificacion;
}
