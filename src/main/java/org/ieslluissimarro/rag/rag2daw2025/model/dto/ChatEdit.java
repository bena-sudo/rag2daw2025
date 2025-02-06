package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatEdit {

    @Schema(example = "1234", description = "Id numérica del chat")
    @NotNull
    private Long idChat;

    @NotNull
    @Schema(example = "Manolo", description = "Nombre del usuario que abre el chat")
    private String usuario;

    @Schema(example = "DD/MM/YYYY HH:MM:SS", description = "Fecha de tipo LocalDate de la creación del chat")
    private LocalDate fecha;
    
    @Schema(description = "Listado de las preguntas asociadas al chat")
    private List<PreguntaDb> preguntas;
}
