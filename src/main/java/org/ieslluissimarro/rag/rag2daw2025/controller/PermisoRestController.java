package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.PermisoList;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.PermisoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/permisos")
public class PermisoRestController {

    @Autowired
    private PermisoServiceImpl permisoService;

    
    /**
     * Obtiene todos los permisos disponibles en el sistema.
     *
     * @return Lista de permisos disponibles.
     */
    @Operation(summary = "Obtener todos los permisos", description = "Devuelve una lista con todos los permisos registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PermisoList.class)) }),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. Se requiere permiso de administrador")
    })
    @PreAuthorize("@authorizationService.hasPermission('VER_PERMISOS')")
    @GetMapping
    public ResponseEntity<List<PermisoList>> getAllPermisos() {
        List<PermisoList> permisos = permisoService.findAllPermisos();
        return ResponseEntity.ok(permisos);
    }

}
