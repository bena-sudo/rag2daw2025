package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;

public interface RagService {
    public List<DocumentoChunkList> subirDoc(Long documentoId);
    public DocumentoChunkInfo confirmarChunk(Long chunkId);
}
