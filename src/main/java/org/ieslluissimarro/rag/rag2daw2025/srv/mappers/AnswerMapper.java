package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.AnswerDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.AnswerList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    AnswerInfo AnswerDbToAnswerInfo(AnswerDb answerDb);
    AnswerList AnswerDbToAnswerList(AnswerDb answerDb);
    AnswerEdit AnswerDbToAnswerEdit(AnswerDb answerDb);
    AnswerDb AnswerEditToAnswerDb(AnswerEdit answerEdit);
    List<AnswerList> answerstoAnswerList(List<AnswerDb> answersDb);
}
