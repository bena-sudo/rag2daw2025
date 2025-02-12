package org.ieslluissimarro.rag.rag2daw2025.srv;

public interface AuditoriaEventoService {
    public void registrarEvento(Long usuarioId, String tipoEvento, String tablaAfectada, String datoAnterior, String datoNuevo, String descripcion);
}
