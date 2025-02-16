package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;

public interface AuditoriaEventoService {
    
    public void registrarEvento(Long usuarioId, String tipoEvento, String tablaAfectada, String datoAnterior, String datoNuevo, String descripcion);

    List<AuditoriaEventoDb> findByUsuarioId(Long usuarioId);
}
