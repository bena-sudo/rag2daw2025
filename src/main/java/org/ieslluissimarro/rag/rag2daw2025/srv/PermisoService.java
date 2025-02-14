package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PermisoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PermisoList;

public interface PermisoService {
    public List<PermisoDb> findAllByIds(Set<Long> ids);
    List<PermisoList> findAllPermisos();
}
