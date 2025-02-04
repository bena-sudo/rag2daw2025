package org.ieslluissimarro.rag.rag2daw2025.model.db;

import java.time.LocalDate;

import jakarta.persistence.Id;

public class DocumentChunksDb {

    @Id
    private Long idDocumentChunk;

    private LocalDate dateCreated;




}
