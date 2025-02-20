package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaList;

public interface EtiquetaService {
    public EtiquetaEdit create(EtiquetaEdit etiquetaEdit);

    public EtiquetaEdit read(Long id);

    public EtiquetaEdit update(Long id, EtiquetaEdit etiquetaEdit);

    public void delete(Long id);

    PaginaResponse<EtiquetaList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

    public PaginaResponse<EtiquetaList> findAll(List<String> filter, int page, int size, List<String> sort)
            throws FiltroException;
}
