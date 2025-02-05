package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="feedbacks")
public class FeedbackDb {

    @Id
    @Column(name = "id_feedback")
    private Long idFeedback;
    @Column(name = "id_pregunta")
    private Long idPregunta;
    @Column(name = "user")
    private String usuario;
    @Column(name = "valoracion")
    private String feedback;

}
