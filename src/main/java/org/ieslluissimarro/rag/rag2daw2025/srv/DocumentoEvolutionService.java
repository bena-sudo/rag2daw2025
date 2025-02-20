package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.estadisticas.DocumentoEvolution;

public interface DocumentoEvolutionService {
    List<DocumentoEvolution> getDocumentosPorDia();
    
    // Método a invocar cuando se crea o modifica un documento
    void guardarDocumento(Object documento);
}
