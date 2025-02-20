package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SesionActivaResponse {
    private Long usuarioId;
    private String email;
    private String nickname;
    private LocalDateTime fechaActivacion;
}
