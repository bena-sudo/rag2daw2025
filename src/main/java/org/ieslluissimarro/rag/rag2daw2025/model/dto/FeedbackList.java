package org.ieslluissimarro.rag.rag2daw2025.model.dto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackList {

    @Id
    private Long idFeedback;
    private Long idAnswer;
    private String user;
    private String feedback;

}
