package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackList;
import org.springframework.data.domain.Sort;


public interface FeedbackService {
    public Optional<FeedbackInfo> getFeedbackInfoById( Long id);
    public List<FeedbackList> findAllFeedbackList();
    public List<FeedbackList> findAllFeedbackList( Sort sort);
}