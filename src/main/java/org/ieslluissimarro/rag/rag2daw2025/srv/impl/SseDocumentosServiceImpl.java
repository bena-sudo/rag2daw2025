package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.ieslluissimarro.rag.rag2daw2025.srv.SseDocumentosService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SseDocumentosServiceImpl implements SseDocumentosService {
    
    // Almacenamos los SseEmitter en un Set concurrente
    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    @Override
    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    @Override
    public void sendActualizacionDocumentos(Object data) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("documentos")
                        .data(data));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        });
    }
    
    // Envío periódico de "heartbeat" para mantener la conexión activa
    @Scheduled(fixedRate = 10000) // cada 10 segundos
    public void sendHeartbeat() {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("heartbeat").data("keep-alive"));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        });
    }
}
