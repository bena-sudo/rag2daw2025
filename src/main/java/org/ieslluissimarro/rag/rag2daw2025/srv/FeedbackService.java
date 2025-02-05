package org.ieslluissimarro.rag.rag2daw2025.srv;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.FeedbackEdit;

public interface FeedbackService {
 
    public FeedbackEdit create(Long idPregunta, String user, String valoracion );
    public FeedbackEdit read(Long id);
    public FeedbackEdit update(Long id, FeedbackEdit feedbackEdit);
    public void delete(Long id);
}
