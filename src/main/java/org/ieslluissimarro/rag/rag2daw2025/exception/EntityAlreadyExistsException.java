package org.ieslluissimarro.rag.rag2daw2025.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public EntityAlreadyExistsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
