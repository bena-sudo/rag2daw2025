package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ChatEdit {

    @Schema(example = "1234", description = "Id numérica del chat")
    private Long idChat;
    @Schema(example = "Manolo", description = "Nombre del usuario que abre el chat")
    private String usuario;
    @Schema(example = "DD/MM/YYYY HH:MM:SS", description = "Fecha de tipo LocalDate de la creación del chat")
    private LocalDate fecha;
    @Schema(description = "Listado de las preguntas asociadas al chat")
    private List<PreguntaDb> preguntas;
}
