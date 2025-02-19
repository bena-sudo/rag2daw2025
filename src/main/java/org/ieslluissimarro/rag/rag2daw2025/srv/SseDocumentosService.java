package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseDocumentosService {
    void addEmitter(SseEmitter emitter);
    void sendActualizacionDocumentos(Object data);
}
