package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;

public interface DocumentoChunkService {
    public DocumentoChunkEdit create(DocumentoChunkEdit documentoChunkEdit);
    public DocumentoChunkInfo read(Long id);
    public DocumentoChunkEdit update(Long id, DocumentoChunkEdit documentoChunkEdit);
    public void delete(Long id);
    public List<DocumentoChunkEdit> guardarChunks(List<DocumentoChunkEdit> chunks);

    PaginaResponse<DocumentoChunkList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<DocumentoChunkList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
