package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AuditoriaEventoList;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.AuditoriaEventoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auditorias")
public class AuditoriaRestController {

    @Autowired
    private AuditoriaEventoServiceImpl auditoriaService;

    @PreAuthorize("@authorizationService.hasPermission('VER_AUDITORIA')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaEventoList>> getAuditoriasByUsuarioId(@PathVariable Long usuarioId) {
        List<AuditoriaEventoList> auditorias = auditoriaService.findByUsuarioId(usuarioId);

        if (auditorias == null || auditorias.isEmpty()) {
            throw new EntityNotFoundException("USER_NOT_FOUND", "No se encontró el usuario con ID: " + usuarioId);
        }
        return ResponseEntity.ok(auditorias);
    }
}
