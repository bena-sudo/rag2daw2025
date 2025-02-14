package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BloqueoCuentaList {
    private Long id;
    private Long usuarioId;
    private String email;
    private int intentosFallidos;
    private boolean bloqueado;
    private LocalDateTime fechaBloqueo;

    public BloqueoCuentaList(Long id, Long usuarioId, String email, int intentosFallidos, boolean bloqueado, LocalDateTime fechaBloqueo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.email = email;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
        this.fechaBloqueo = fechaBloqueo;
    }
}

