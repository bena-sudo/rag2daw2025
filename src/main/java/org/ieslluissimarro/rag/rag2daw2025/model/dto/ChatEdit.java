package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;

import jakarta.persistence.Id;
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

    @Id
    private Long idChat;
    private String user;
    private LocalDate date;


}
