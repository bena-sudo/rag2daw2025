package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.ieslluissimarro.rag.rag2daw2025.repository.SesionActivaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SesionCleanupService {

    private final SesionActivaRepository sesionActivaRepository;

    public SesionCleanupService(SesionActivaRepository sesionActivaRepository) {
        this.sesionActivaRepository = sesionActivaRepository;
    }

    @Scheduled(fixedRate = 600000) // Ejecuta cada 10 minutos
    public void limpiarSesionesExpiradas() {
        sesionActivaRepository.deleteAll(sesionActivaRepository.findByFechaExpiracionBefore(LocalDateTime.now()));
    }
}
