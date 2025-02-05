package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentChunksList {

    @Id
    private Long idDocumentChunk;
    private Long idDocSource;
    private LocalDate fechaCreado;
}
