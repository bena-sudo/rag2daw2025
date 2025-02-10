package org.ieslluissimarro.rag.rag2daw2025.srv;

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

    public PaginaResponse<EtiquetaList> findAll(String[] filter, int page, int size, String[] sort)
            throws FiltroException;
}
