package org.ieslluissimarro.rag.rag2daw2025.event;

import org.springframework.context.ApplicationEvent;

public class DocumentoActualizadoEvent extends ApplicationEvent {
    public DocumentoActualizadoEvent(Object source) {
        super(source);
    }
}
