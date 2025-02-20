package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaCuestionarioEdit;

public interface PreguntaCuestionarioService {
    PreguntaCuestionarioEdit create(PreguntaCuestionarioEdit preguntaEdit);
    PreguntaCuestionarioEdit read(Long id);
    PreguntaCuestionarioEdit update(Long id, PreguntaCuestionarioEdit preguntaEdit);
    void delete(Long id);
    List<PreguntaCuestionarioEdit> getAllPreguntas();
    List<PreguntaCuestionarioEdit> obtenerPreguntasPorCuestionario(Long cuestionarioId);
}
