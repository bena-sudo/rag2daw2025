package org.ieslluissimarro.rag.rag2daw2025.exception;

import lombok.Getter;

@Getter
public class DataValidationException extends RuntimeException {
    
    private final String errorCode;

    public DataValidationException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
