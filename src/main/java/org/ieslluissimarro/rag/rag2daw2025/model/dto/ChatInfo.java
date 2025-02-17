package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatInfo {

    
    private Long idChat;
    private String usuario;
    private LocalDateTime fecha;
    private Integer contexto;

}
