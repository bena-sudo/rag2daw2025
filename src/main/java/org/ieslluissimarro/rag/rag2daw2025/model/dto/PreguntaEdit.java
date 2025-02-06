package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreguntaEdit {
    
    @Schema(example = "1234", description = "ID numérica de la pregunta")
    private Long idPregunta;
    @NotNull

    @Schema(example = "1234", description = "ID numérica del chat")
    private Long idChat;
    @NotNull
    @Schema(example = "Manolo", description = "Nombre del usuario que realiza la pregunta")
    private String usuario;
    @Schema(example = "Pregunta...", description = "Texto que el usuario envia al RAG")
    private String textoPregunta;
    @Schema(example = "Respuesta...", description = "Texto que el RAG devuelve al usuario. Por defecto se crea vacío")
    private String textoRespuesta;
    @Schema(example = "NORMAL", description = "Enum con valores BIEN, NORMAL y MAL. Por defecto es normal")
    private String feedback;
    @Schema(example = "false", description = "Booleano que indica si el mensaje ha sido valorado")
    private Boolean valorado;

}
