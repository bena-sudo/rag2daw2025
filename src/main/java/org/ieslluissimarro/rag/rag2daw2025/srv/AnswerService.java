package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AnswerDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerList;
import org.springframework.data.domain.Sort;

import io.micrometer.common.lang.NonNull;

public interface AnswerService {
    public Optional<AnswerInfo> getAnswerInfoById( Long id);
    public List<AnswerList> findAllAnswerList();
    public List<AnswerList> findAllAnswerList( Sort sort);
    public @NonNull List<AnswerDb> findByTextContaining( String text,  Sort sort);
}