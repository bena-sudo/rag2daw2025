package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ModuloEdit;

public interface ModuloService {
    ModuloEdit create(ModuloEdit moduloEdit);
    ModuloEdit read(Long id);
    ModuloEdit update(Long id, ModuloEdit moduloEdit);
    void delete(Long id);

    // List<ModuloEdit> getAllModulos();
    List<ModuloEdit> getModulosBySector(Long sectorId);

    PaginaResponse<ModuloEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<ModuloEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}