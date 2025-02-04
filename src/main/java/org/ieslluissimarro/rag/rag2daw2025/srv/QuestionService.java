package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.QuestionDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionList;
import org.springframework.data.domain.Sort;

import io.micrometer.common.lang.NonNull;


public interface QuestionService {
    public Optional<QuestionInfo> getQuestionInfoById( Long id);
    public List<QuestionList> findAllQuestionList();
    public List<QuestionList> findAllQuestionList( Sort sort);
    public @NonNull List<QuestionDb> findByTextContaining( String text,  Sort sort);

}