package org.ieslluissimarro.rag.rag2daw2025.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ieslluissimarro.rag.rag2daw2025.event.DocumentoActualizadoEvent;
import org.ieslluissimarro.rag.rag2daw2025.srv.DocumentoEvolutionService;
import org.ieslluissimarro.rag.rag2daw2025.srv.SseDocumentosService;

@Configuration
public class SseConfiguration {

    @Bean
    public ApplicationListener<DocumentoActualizadoEvent> documentoActualizadoEventListener(
            DocumentoEvolutionService documentoEvolutionService,
            SseDocumentosService sseDocumentosService) {
        return event -> {
            // Obtenemos los datos actualizados y se los enviamos a los clientes SSE
            var datosActualizados = documentoEvolutionService.getDocumentosPorDia();
            sseDocumentosService.sendActualizacionDocumentos(datosActualizados);
        };
    }
}
