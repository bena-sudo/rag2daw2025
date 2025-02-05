package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.QuestionDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.QuestionList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionInfo QuestionDbToQuestionInfo(QuestionDb questionDb);
    QuestionList QuestionDbToQuestionList(QuestionDb questionDb);
    QuestionEdit QuestionDbToQuestionEdit(QuestionDb questionDb);
    QuestionDb QuestionEditToQuestionDb(QuestionEdit questionEdit);
    List<QuestionList> questionstoQuestionList(List<QuestionDb> questionsDb);
}
