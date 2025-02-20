package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoEvolutionService;
import org.ieslluissimarro.rag.rag2daw2025.srv.SseDocumentosService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("api/v1/sse")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentoSseController {

    private final SseDocumentosService sseDocumentosService;
    private final DocumentoEvolutionService documentoEvolutionService;

    public DocumentoSseController(SseDocumentosService sseDocumentosService,
                                  DocumentoEvolutionService documentoEvolutionService) {
        this.sseDocumentosService = sseDocumentosService;
        this.documentoEvolutionService = documentoEvolutionService;
    }

    @PreAuthorize("@authorizationService.hasPermission('VER_DOCUMENTOS')")
    @GetMapping(value = "/documentos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamDocumentos() {
        // Timeout 0L: conexión sin límite
        SseEmitter emitter = new SseEmitter(0L);
        sseDocumentosService.addEmitter(emitter);
        // Se envían los datos iniciales
        sseDocumentosService.sendActualizacionDocumentos(documentoEvolutionService.getDocumentosPorDia());
        return emitter;
    }
}
