package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.RolList;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;

import java.util.List;

public interface RolService {
    List<RolList> findAllRoles();
    List<UsuarioDb> findUsuariosByRolNombre(RolNombre rolNombre);
    RolDb findById(Long id);
    RolDb save(RolDb rol);
}