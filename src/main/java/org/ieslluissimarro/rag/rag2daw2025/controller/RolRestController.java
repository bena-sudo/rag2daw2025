package org.ieslluissimarro.rag.rag2daw2025.controller;

import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    /**
     * Obtiene todos los roles disponibles en el sistema.
     *
     * @return Lista de roles disponibles.
     */
    @Operation(summary = "Obtener todos los roles", description = "Devuelve la lista de todos los roles registrados en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RolList.class)) })
    })
    @GetMapping
    public ResponseEntity<List<RolList>> getAllRoles() {
        List<RolList> roles = rolService.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Obtiene todos los usuarios asignados a un rol específico.
     *
     * @param rolNombre Nombre del rol.
     * @return Lista de usuarios asociados al rol.
     */
    @Operation(summary = "Obtener usuarios por rol", description = "Devuelve una lista de usuarios asociados a un rol específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioInfo.class)) }),
            @ApiResponse(responseCode = "400", description = "Error: Rol inválido", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios con este rol", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{rolNombre}/usuarios")
    public ResponseEntity<List<UsuarioInfo>> getUsuariosByRol(@PathVariable String rolNombre) {
        RolNombre rol = RolNombre.valueOf(rolNombre);
        List<UsuarioDb> usuarios = rolService.findUsuariosByRolNombre(rol);

        return ResponseEntity.ok(UsuarioMapper.INSTANCE.usuariosDbToUsuarioInfo(usuarios));
    }

    /**
     * Obtiene los permisos asociados a un rol específico.
     *
     * @param id ID del rol.
     * @return Lista de permisos asociados al rol.
     */
    @Operation(summary = "Obtener permisos de un rol", description = "Devuelve los permisos asociados a un rol específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RolListPermiso.class)) }),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    @GetMapping("/{id}/permisos")
    public ResponseEntity<RolListPermiso> getPermisosByRol(@PathVariable Long id) {
        RolDb rol = rolService.findById(id);
        RolListPermiso rolListPermiso = RolMapper.INSTANCE.rolesDbToRolListPermiso(rol);
        return ResponseEntity.ok(rolListPermiso);
    }

    /**
     * Asigna permisos a un rol específico.
     *
     * @param id         ID del rol.
     * @param permisoIds Lista de IDs de permisos a asignar.
     * @return Rol actualizado con los permisos asignados.
     */
    @Operation(summary = "Asignar permisos a un rol", description = "Asigna una lista de permisos a un rol existente en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permisos asignados correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RolDb.class)) }),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class)) })
    })
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
