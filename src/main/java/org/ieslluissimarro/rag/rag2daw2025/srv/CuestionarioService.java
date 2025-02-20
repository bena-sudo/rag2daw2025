package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.CuestionarioEdit;

public interface CuestionarioService {
    CuestionarioEdit create(CuestionarioEdit cuestionarioEdit);
    CuestionarioEdit read(Long id);
    CuestionarioEdit update(Long id, CuestionarioEdit cuestionarioEdit);
    void delete(Long id);
    
    PaginaResponse<CuestionarioEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<CuestionarioEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}