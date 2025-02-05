package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatInfo {


    private Long idChat;
    private String usuario;
    private LocalDate fecha;
    private List<PreguntaInfo> preguntas;
}
