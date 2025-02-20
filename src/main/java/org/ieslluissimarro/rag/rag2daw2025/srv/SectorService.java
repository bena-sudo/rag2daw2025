package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.SectorEdit;

public interface SectorService {
    SectorEdit create(SectorEdit sectorEdit);
    SectorEdit read(Long id);
    SectorEdit update(Long id, SectorEdit sectorEdit);
    void delete(Long id);

    // List<SectorEdit> getAllSectores();

    PaginaResponse<SectorEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<SectorEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}