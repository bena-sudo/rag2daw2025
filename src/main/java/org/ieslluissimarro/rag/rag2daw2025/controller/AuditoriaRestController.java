package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;
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

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaEventoDb>> getAuditoriasByUsuarioId(@PathVariable Long usuarioId) {
        List<AuditoriaEventoDb> auditorias = auditoriaService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(auditorias);
    }
}
