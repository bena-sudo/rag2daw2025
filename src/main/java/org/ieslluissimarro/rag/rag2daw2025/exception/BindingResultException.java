package org.ieslluissimarro.rag.rag2daw2025.exception;

import java.util.Map;
import lombok.Getter;

@Getter
/**
 * Crea una excepción de validación asociada con un BindingResult.
 * 
 * @param errorCode        Código de error que identifica el contexto de
 *                         validación.
 * @param validationErrors Mapa de errores de validación (campo => mensaje de
 *                         error).
 */
public class BindingResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final Map<String, String> validationErrors;

    public BindingResultException(String errorCode, Map<String, String> validationErrors) {
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
    }
}
