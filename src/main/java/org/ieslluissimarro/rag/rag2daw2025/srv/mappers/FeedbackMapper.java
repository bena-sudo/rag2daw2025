package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.FeedbackDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    FeedbackInfo FeedbackDbToFeedbackInfo(FeedbackDb feedbackDb);
    FeedbackList FeedbackDbToFeedbackList(FeedbackDb feedbackDb);
    FeedbackEdit FeedbackDbToFeedbackEdit(FeedbackDb feedbackDb);
    FeedbackDb FeedbackEditToFeedbackDb(FeedbackEdit feedbackEdit);
    List<FeedbackList> feedbackstoFeedbackList(List<FeedbackDb> feedbacksDb);
}
