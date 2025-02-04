package main.java.org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="questions")
public class QuestionDb {

    @Id
    private Long idQuestion;
    private Long idChat;
    private String user;
    private String text;
   // private List
}
