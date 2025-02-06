package org.ieslluissimarro.rag.rag2daw2025.security.service.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.RolRepository;
import org.ieslluissimarro.rag.rag2daw2025.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import java.util.Optional;

@Service
@Transactional//Mantiene la coherencia de la BD si hay varios accesos de escritura concurrentes
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<RolDb> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByNombre(rolNombre);
    }

    public void save(@NonNull RolDb rol){
        rolRepository.save(rol);
    }
}


