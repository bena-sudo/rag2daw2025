package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoChunk;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentoChunkEdit implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private Integer idDocRag;

    @NotNull(message = "El orden del chunk no puede ser nulo")
    private Integer chunkOrder;

    @NotNull(message = "El texto del chunk no puede estar vacío")
    @Size(min = 1, message = "El texto del chunk no puede estar vacío")
    private String chunkText;

    private String chunkedBy;

    private EstadoChunk estado;

    private LocalDateTime fechaModificacion;
}
