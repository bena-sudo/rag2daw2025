package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="answers")
@Data
public class AnswerDb {

    @Id
    private Long idAnswer;

    private Long idQuestion;

    private String user;
    private String text;

    @OneToOne
    @JoinColumn(name = "idChat", referencedColumnName = "idChat")
    private Long idChat;


}
