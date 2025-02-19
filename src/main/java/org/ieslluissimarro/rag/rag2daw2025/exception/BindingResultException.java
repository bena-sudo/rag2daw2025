package org.ieslluissimarro.rag.rag2daw2025.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class BindingResultException extends RuntimeException{

    private final String errorCode;
    private final Map<String,String> validationErrors;

    public BindingResultException(String errorcode, Map<String, String> validation){
        this.errorCode = errorcode;
        this.validationErrors = validation;
    }

}
