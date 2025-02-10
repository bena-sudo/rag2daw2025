package org.ieslluissimarro.rag.rag2daw2025.exception;

import java.util.Map;

import lombok.Getter;

@Getter
/**
 * Crea una exception de validacion asoaciada con un BindingResult
 * 
 * @param errorCode        Codigo de error que identifica el contexto de
 *                         validacion
 * 
 * @param validationErrors Mapa de errores de validacion (campo -> mensaje de
 *                         error)
 * 
 */
public class BindingResultException extends RuntimeException {
    private final String errorCode;
    private final Map<String, String> validationErrors;

    public BindingResultException(String errorCode, Map<String, String> validationErrors) {
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
    }
}
