package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AnswerDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerList;
import org.ieslluissimarro.rag.rag2daw2025.srv.AnswerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Override
    public Optional<AnswerInfo> getAnswerInfoById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnswerInfoById'");
    }

    @Override
    public List<AnswerList> findAllAnswerList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllAnswerList'");
    }

    @Override
    public List<AnswerList> findAllAnswerList(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllAnswerList'");
    }

    @Override
    public List<AnswerDb> findByTextContaining(String text, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTextContaining'");
    }
    
}
