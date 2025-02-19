package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;
import java.util.List;
import org.ieslluissimarro.rag.rag2daw2025.event.DocumentoActualizadoEvent;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.estadisticas.DocumentoEvolution;
import org.ieslluissimarro.rag.rag2daw2025.repository.estadisticas.EstadisticasDocumentosRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoEvolutionService;

@Service
public class DocumentoEvolutionServiceImpl implements DocumentoEvolutionService {
    
    private final EstadisticasDocumentosRepository estadisticasDocumentosRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DocumentoEvolutionServiceImpl(EstadisticasDocumentosRepository estadisticasDocumentosRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.estadisticasDocumentosRepository = estadisticasDocumentosRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<DocumentoEvolution> getDocumentosPorDia() {
        return estadisticasDocumentosRepository.documentosPorDia();
    }

    @Override
    public void guardarDocumento(Object documento) {
        // Aquí guardarías el documento, por ejemplo:
        // documentoRepository.save((DocumentoDB) documento);

        // Publicamos el evento para notificar a los clientes SSE
        eventPublisher.publishEvent(new DocumentoActualizadoEvent(this));
    }
}
