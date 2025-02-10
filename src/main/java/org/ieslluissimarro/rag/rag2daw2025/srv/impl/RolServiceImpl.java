package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import org.ieslluissimarro.rag.rag2daw2025.model.db.RolDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.UsuarioDb;
import org.ieslluissimarro.rag.rag2daw2025.model.enums.RolNombre;
import org.ieslluissimarro.rag.rag2daw2025.repository.RolRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<RolDb> findAllRoles() {
        return rolRepository.findAll();
    }

    @Override
    public List<UsuarioDb> findUsuariosByRolNombre(RolNombre rolNombre) {
        return rolRepository.findUsuariosByRolNombre(rolNombre);
    }

}