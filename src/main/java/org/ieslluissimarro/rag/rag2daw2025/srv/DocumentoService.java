package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;

public interface DocumentoService {
    
    DocumentoEdit create(DocumentoEdit documentoEdit);
    DocumentoEdit read(Long id);
    DocumentoEdit update(Long id, DocumentoEdit documentoEdit);
    void delete(Long id);
    PaginaResponse<DocumentoList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<DocumentoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

}
