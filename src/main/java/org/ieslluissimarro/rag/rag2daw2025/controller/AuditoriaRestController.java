package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AuditoriaEventoDb;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.AuditoriaEventoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auditorias")
public class AuditoriaRestController {

    @Autowired
    private AuditoriaEventoServiceImpl auditoriaService;

    /**
     * Obtiene la lista de eventos de auditoría de un usuario específico.
     *
     * @param usuarioId ID del usuario cuyos eventos de auditoría se quieren
     *                  consultar.
     * @return Lista de eventos de auditoría asociados al usuario.
     */
    @Operation(summary = "Obtener auditorías de un usuario", description = "Devuelve la lista de eventos de auditoría registrados para un usuario específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de eventos de auditoría obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuditoriaEventoDb.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron eventos de auditoría para el usuario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaEventoDb>> getAuditoriasByUsuarioId(@PathVariable Long usuarioId) {
        List<AuditoriaEventoDb> auditorias = auditoriaService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(auditorias);
    }
}
