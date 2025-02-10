package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.srv.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolRestController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolDb>> getAllRoles() {
        List<RolDb> roles = rolService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{rolNombre}/usuarios")
    public ResponseEntity<List<UsuarioDb>> getUsuariosByRol(@PathVariable String rolNombre) {
        RolNombre rol = RolNombre.valueOf(rolNombre);
        List<UsuarioDb> usuarios = rolService.findUsuariosByRolNombre(rol);
        return ResponseEntity.ok(usuarios);
    }
}
