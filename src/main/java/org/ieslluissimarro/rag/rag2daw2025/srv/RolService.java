package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;

import java.util.List;

public interface RolService {
    List<RolDb> findAllRoles();
    List<UsuarioDb> findUsuariosByRolNombre(RolNombre rolNombre);
}