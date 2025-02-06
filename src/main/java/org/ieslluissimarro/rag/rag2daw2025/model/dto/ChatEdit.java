package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatEdit {
    
    @Nullable
    @Schema(example = "1234", description = "Id numérica del chat")
    private Long idChat;

    @NotNull
    @Schema(example = "Manolo", description = "Nombre del usuario que abre el chat")
    private String usuario;

    @Nullable
    @Schema(example = "DD/MM/YYYY HH:MM:SS", description = "Fecha de tipo LocalDate de la creación del chat")
    private LocalDateTime fecha;

    @Schema(description = "1")
    @NotNull
    private Integer contexto;

}
