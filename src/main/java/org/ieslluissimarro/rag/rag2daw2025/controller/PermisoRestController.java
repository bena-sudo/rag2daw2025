package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.PermisoList;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.PermisoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permisos")
public class PermisoRestController {
    

    @Autowired
    private PermisoServiceImpl permisoService;

    @PreAuthorize("@authorizationService.hasPermission('VER_PERMISOS')")
    @GetMapping
    public ResponseEntity<List<PermisoList>> getAllPermisos() {
        List<PermisoList> permisos = permisoService.findAllPermisos();
        return ResponseEntity.ok(permisos);
    }


}
