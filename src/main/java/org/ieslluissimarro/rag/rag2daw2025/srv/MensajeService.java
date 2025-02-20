package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.filters.model.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.MensajeEdit;

public interface MensajeService {
    MensajeEdit create(MensajeEdit mensajeEdit);
    MensajeEdit read(Long id);
    MensajeEdit update(Long id, MensajeEdit mensajeEdit);
    void delete(Long id);
    
    PaginaResponse<MensajeEdit> findAll(List<String> filter, int page, int size, List<String> sort) throws FiltroException;
    PaginaResponse<MensajeEdit> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

    List<MensajeEdit> getMensajesByUsuarioId(Long usuarioId);
    List<MensajeEdit> getMensajesByAcreditacionId(Long acreditacionId);
}
