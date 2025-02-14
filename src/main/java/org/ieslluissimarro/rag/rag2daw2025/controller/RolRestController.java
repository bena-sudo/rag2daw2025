package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PermisoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RolList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RolListPermiso;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.UsuarioInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.srv.RolService;
import org.ieslluissimarro.rag.rag2daw2025.srv.impl.PermisoServiceImpl;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.RolMapper;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/roles")
public class RolRestController {

    @Autowired
    private RolService rolService;

    @Autowired
    private PermisoServiceImpl permisoService;

    @GetMapping
    public ResponseEntity<List<RolList>> getAllRoles() {
        List<RolList> roles = rolService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{rolNombre}/usuarios")
    public ResponseEntity<List<UsuarioInfo>> getUsuariosByRol(@PathVariable String rolNombre) {
        RolNombre rol = RolNombre.valueOf(rolNombre);
        List<UsuarioDb> usuarios = rolService.findUsuariosByRolNombre(rol);
        
        return ResponseEntity.ok(UsuarioMapper.INSTANCE.usuariosDbToUsuarioInfo(usuarios));
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{id}/permisos")
    public ResponseEntity<RolListPermiso> getPermisosByRol(@PathVariable Long id) {
        RolDb rol = rolService.findById(id);
        RolListPermiso rolListPermiso = RolMapper.INSTANCE.rolesDbToRolListPermiso(rol);
        return ResponseEntity.ok(rolListPermiso);
    }

    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @PostMapping("/{id}/permisos")
    public ResponseEntity<RolDb> assignPermisosToRol(@PathVariable Long id, @RequestBody Set<Long> permisoIds) {
        RolDb rol = rolService.findById(id);
        if (rol == null) {
            throw new EntityNotFoundException("ROL_NOT_FOUND", "Rol no encontrado");
        }
        List<PermisoDb> permisosList = permisoService.findAllByIds(permisoIds);
        Set<PermisoDb> permisosSet = new HashSet<>(permisosList);
        rol.setPermisos(permisosSet);
        RolDb updatedRol = rolService.save(rol);
        return ResponseEntity.ok(updatedRol);
    }
}
