package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PermisoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.repository.UsuarioRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service("authorizationService") // Nombre personalizado para el bean
@RequiredArgsConstructor
@Transactional // Mantiene la coherencia de la BD si hay varios accesos de escritura concurrentes
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    UsuarioRepository usuarioRepository;
    // private final PermissionRepository permissionRepository;

    @PreAuthorize("isAuthenticated()")
    @Override
    public boolean hasPermission(String permissionName) {
        UsuarioDb user = getCurrentUser();
        
        for (RolDb rol : user.getRoles()) {
            for (PermisoDb permiso : rol.getPermisos()) {
                if (permiso.getNombre().name().equals(permissionName)) {
                    return true;
                }
            }
        }
        return false;
        //return user.getRoles().stream().flatMap(rol -> rol.getPermisos().stream()).anyMatch(permiso -> permiso.getNombre().name().equals(permissionName));
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public boolean hasOwnAcreditacionPermision(Long acreditacionId, String permissionName) {
        UsuarioDb user = getCurrentUser();
        // TODO Auto-generated method stub
        // return true;
        throw new UnsupportedOperationException("Unimplemented method 'hasOwnAcreditacionPermission(Long acreditacionId, String permissionName)'");
        // Aquí deberíamos implementar la lógica de autorización consultando usuarios y roles en la BD
        // y verificando que el usuario tiene permiso para acceder a la acreditación con id acreditacionId.
        // Esto puede servir para verificar que es su acreditación y que aunque tenga permisos de modificación,
        // si no es el candidato, el asesor o el evaluador no puede modificar nada.
    }

    private UsuarioDb getCurrentUser() {
        // Obtener usuario completo desde el contexto
        UserDetails user = UserDetailsServiceImpl.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        return usuarioRepository.findByEmail(user.getUsername()).get();
    }
}