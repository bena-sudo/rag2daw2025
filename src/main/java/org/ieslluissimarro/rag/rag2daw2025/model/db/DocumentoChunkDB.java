package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.io.Serializable;
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
@Table(name = "documentos_chunks")
public class DocumentoChunkDB implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del documento no puede ser nulo.")
    @Column(name = "id_documento")
    private Long idDocumento;

    @Column(name = "id_doc_rag")
    private Integer idDocRag;

    @NotNull(message = "El orden del chunk no puede ser nulo.")
    @Column(name = "chunk_order")
    private Integer chunkOrder;

    @NotNull(message = "El texto del chunk no puede estar vacío.")
    @Size(min = 1)
    @Column(name = "chunk_text")
    @Lob
    private String chunkText;

    @Column(name = "chunked_by")
    private String chunkedBy;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'pendiente'")
    private EstadoChunk estado;

    @Column(name = "fecha_creacion", insertable=false , updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
}
