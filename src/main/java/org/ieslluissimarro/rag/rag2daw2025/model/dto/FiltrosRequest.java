package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltrosRequest {

    @Nullable
    private String filterUser;
    @Nullable
    private String filterRango;
    @Nullable

    private String filterRespuesta;
    @Nullable

    private String filterPregunta;
    @Nullable

    private String filterFeedback;
    @Nullable

    private String filterChunk;

}
