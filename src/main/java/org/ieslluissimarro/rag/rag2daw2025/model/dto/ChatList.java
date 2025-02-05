package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatList {

    @Id
    private Long idChat;
    private String usuario;
    private LocalDate fecha;
}
