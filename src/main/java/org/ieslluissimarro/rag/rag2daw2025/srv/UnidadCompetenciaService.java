package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UnidadCompetenciaEdit;

public interface UnidadCompetenciaService {
    UnidadCompetenciaEdit create(UnidadCompetenciaEdit unidadCompetenciaEdit);
    UnidadCompetenciaEdit read(String id);
    UnidadCompetenciaEdit update(String id, UnidadCompetenciaEdit unidadCompetenciaEdit);
    void delete(String id);

    // List<UnidadCompetenciaEdit> getAllUnidadCompetencias();
    List<UnidadCompetenciaEdit> getUnidadesCompetenciaByModulo(Long moduloId);

    PaginaResponse<UnidadCompetenciaEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<UnidadCompetenciaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}
