package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.IdEntityLong;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.DocumentoChunkList;
import org.ieslluissimarro.rag.rag2daw2025.srv.RagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rag")
public class RagController {
    private final RagService ragService;
    
    @PreAuthorize("@authorizationService.hasPermission('CREAR_DOCUMENTO')")
    @GetMapping("/subirDocumento/{id}/{idUsuario}")
    public ResponseEntity<List<DocumentoChunkList>> subir(@PathVariable String id, @PathVariable String idUsuario) {
        return ResponseEntity.ok(ragService.subirDoc(new IdEntityLong(id).getValue()/*, usuario*/));
    }

    @PreAuthorize("@authorizationService.hasPermission('EDITAR_CHUNKS')")
    @GetMapping("/confirmChunk/{id}/{idUsuario}")
    public ResponseEntity<DocumentoChunkInfo> confirmar(@PathVariable String id, @PathVariable String idUsuario) {
        return ResponseEntity.ok(ragService.confirmarChunk(new IdEntityLong(id).getValue()));
    }
}
