package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaInfo {
    private Long idChat;
    private Long idPregunta;
    private String usuario;
    private String textoPregunta;
    private String textoRespuesta;
    private String feedback;
    private Boolean valorado;
    

}
