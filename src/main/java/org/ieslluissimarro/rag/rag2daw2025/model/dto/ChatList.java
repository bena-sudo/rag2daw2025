package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDate;

import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatList {

    

    private Long idChat;
    private String usuario;
    private Integer contexto;
  


}
