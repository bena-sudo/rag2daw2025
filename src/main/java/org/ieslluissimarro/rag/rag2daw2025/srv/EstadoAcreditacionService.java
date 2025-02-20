package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EstadoAcreditacionEdit;

public interface EstadoAcreditacionService {
    public EstadoAcreditacionEdit create(EstadoAcreditacionEdit estadoAcreditacionEdit);
    public EstadoAcreditacionEdit read(Long id);
    public EstadoAcreditacionEdit update(Long id, EstadoAcreditacionEdit estadoAcreditacionEdit);
    public void delete(Long id);

    PaginaResponse<EstadoAcreditacionEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<EstadoAcreditacionEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
