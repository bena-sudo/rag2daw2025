package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RespuestaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RespuestaEdit;

public interface RespuestaService {
    RespuestaEdit create(RespuestaEdit respuestaEdit);
    RespuestaEdit read(Long id);
    void delete(Long id);

    // List<RespuestaEdit> getAllRespuestas();
    List<RespuestaDb> createRespuestas(List<RespuestaEdit> respuestas);

    PaginaResponse<RespuestaEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<RespuestaEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;
}