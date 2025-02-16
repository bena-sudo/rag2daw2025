package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.BloqueoCuentaList;
import org.ieslluissimarro.rag.rag2daw2025.repository.BloqueoCuentaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.BloqueoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloqueoCuentaServiceImpl implements BloqueoCuentaService{

    @Autowired
    private BloqueoCuentaRepository bloqueoCuentaRepository;

    @Override
    public List<BloqueoCuentaList> listarCuentasBloqueadas() {
        return bloqueoCuentaRepository.findAllWithEmail();
    }
    
}

