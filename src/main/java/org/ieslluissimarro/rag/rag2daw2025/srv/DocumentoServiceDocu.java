package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoEditDocu;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoNew;

public interface DocumentoServiceDocu {
    
    DocumentoNew create(DocumentoNew documentoNew);
    DocumentoInfo read(Long id);
    DocumentoEditDocu update(Long id, DocumentoEditDocu documentoEdit);
    void delete(Long id);
    PaginaResponse<DocumentoList> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<DocumentoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

}
