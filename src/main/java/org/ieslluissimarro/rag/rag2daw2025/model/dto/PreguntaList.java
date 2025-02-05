package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaList {


    @Id
    private Long idPregunta;
    private Long idChat;
    private String usuario;
    private String texto;
}
