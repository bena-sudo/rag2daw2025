package org.ieslluissimarro.rag.rag2daw2025.security.service;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.springframework.lang.NonNull;


public interface RolService {
    public Optional<RolDb> getByRolNombre(RolNombre rolNombre);
    public void save(@NonNull RolDb rol);
}


