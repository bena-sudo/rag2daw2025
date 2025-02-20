package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;

public interface DocumentoService {
    DocumentoEdit create(DocumentoEdit documento);
    DocumentoEdit read(long id);
    DocumentoEdit update(long id, DocumentoEdit documento);
    void delete(long id);

    PaginaResponse<DocumentoEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<DocumentoEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
