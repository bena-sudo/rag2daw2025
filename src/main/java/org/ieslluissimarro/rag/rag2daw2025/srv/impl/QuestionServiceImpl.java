package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.QuestionDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionList;
import org.ieslluissimarro.rag.rag2daw2025.srv.QuestionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class QuestionServiceImpl implements QuestionService{

    @Override
    public Optional<QuestionInfo> getQuestionInfoById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuestionInfoById'");
    }

    @Override
    public List<QuestionList> findAllQuestionList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllQuestionList'");
    }

    @Override
    public List<QuestionList> findAllQuestionList(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllQuestionList'");
    }

    @Override
    public List<QuestionDb> findByTextContaining(String text, Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTextContaining'");
    }
    
}
