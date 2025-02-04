package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackList;
import org.ieslluissimarro.rag.rag2daw2025.srv.FeedbackService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Override
    public Optional<FeedbackInfo> getFeedbackInfoById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFeedbackInfoById'");
    }

    @Override
    public List<FeedbackList> findAllFeedbackList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllFeedbackList'");
    }

    @Override
    public List<FeedbackList> findAllFeedbackList(Sort sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllFeedbackList'");
    }
    
}
