package org.ieslluissimarro.rag.rag2daw2025.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="preguntas")
public class PreguntaDb {

    @Id
    private Long idPregunta;
    private Long idChat;
    private String usuario;
    private String texto;
   // private List
}
