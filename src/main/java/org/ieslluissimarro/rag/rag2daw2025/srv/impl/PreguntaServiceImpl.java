package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaList;
import org.ieslluissimarro.rag.rag2daw2025.srv.PreguntaService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class PreguntaServiceImpl implements PreguntaService{

    @Override
    public Optional<PreguntaInfo> getPreguntaInfoById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPreguntaInfoById'");
    }

    @Override
    public List<PreguntaList> findAllPreguntaList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllPreguntaList'");
    }

    @Override
    public List<PreguntaList> findAllPreguntaList(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllPreguntaList'");
    }

    @Override
    public List<PreguntaDb> findByTextContaining(String text, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTextContaining'");
    }
    
}
