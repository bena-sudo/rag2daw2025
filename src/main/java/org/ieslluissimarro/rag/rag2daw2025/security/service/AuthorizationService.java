package org.ieslluissimarro.rag.rag2daw2025.security.service;

public interface AuthorizationService {
    public boolean hasPermission(String permissionName);
    public boolean hasOwnAcreditacionPermision(Long resourceId, String permissionName);
}
