package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PermisoDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PermisoList;
import org.ieslluissimarro.rag.rag2daw2025.repository.PermisoRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.PermisoService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mapper.PermisoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<PermisoDb> findAllByIds(Set<Long> ids) {
        return permisoRepository.findAllById(ids);
    }

    @Override
    public List<PermisoList> findAllPermisos() {
        return PermisoMapper.INSTANCE.permisosDbToPermisosList(permisoRepository.findAll());
    }

}