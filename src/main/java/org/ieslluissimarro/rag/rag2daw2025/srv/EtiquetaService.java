package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.EtiquetaEdit;

public interface EtiquetaService {
    public EtiquetaEdit create(EtiquetaEdit etiquetaEdit);

    public EtiquetaEdit read(Long id);

    public EtiquetaEdit update(Long id, EtiquetaEdit etiquetaEdit);

    public void delete(Long id);
}
