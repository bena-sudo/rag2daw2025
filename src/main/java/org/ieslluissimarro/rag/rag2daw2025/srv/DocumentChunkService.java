package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentChunkInfo;

public interface DocumentChunkService {
    public DocumentChunkEdit create(DocumentChunkEdit alumnoEdit);
    public DocumentChunkInfo read(Long id);
    public DocumentChunkEdit update(Long id, DocumentChunkEdit alumnoEdit);
    public void delete(Long id);
}
