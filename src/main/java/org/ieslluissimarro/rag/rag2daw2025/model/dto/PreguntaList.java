package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaList {

    private Long idPregunta;
    private Long idChat;
    private String usuario;
    private String textoPregunta;
    private String textoRespuesta;

}
