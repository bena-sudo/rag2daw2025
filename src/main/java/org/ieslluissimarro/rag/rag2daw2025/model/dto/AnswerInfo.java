package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AnswerInfo {

    @Id
    private Long idAnswer;

    private Long idQuestion;

    private String user;
    private String text;
    private Long idChat;
}
