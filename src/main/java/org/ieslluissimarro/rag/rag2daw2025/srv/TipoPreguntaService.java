package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.TipoPreguntaDb;

public interface TipoPreguntaService {
    
    TipoPreguntaDb create(TipoPreguntaDb tipoPreguntaDb);
    TipoPreguntaDb read(Long id);
    TipoPreguntaDb update(Long id, TipoPreguntaDb tipoPreguntaDb);
    void delete(Long id);
    List<TipoPreguntaDb> getAllTipoPreguntas();
    
}