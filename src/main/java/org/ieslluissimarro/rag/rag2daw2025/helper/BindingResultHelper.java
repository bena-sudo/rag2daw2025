package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.HashMap;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultException;
import org.springframework.validation.BindingResult;

public class BindingResultHelper {

    private BindingResultHelper(){
        throw new UnsupportedOperationException("Esta clase no debe ser instanciada");
    }


    public static Map<String,String> extractErrors(BindingResult bindingResult) {
        Map<String,String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }


    public static void validationBindingResult(BindingResult bindingResult,String errorCode){
        if(bindingResult.hasErrors()){
            throw new BindingResultException(errorCode, BindingResultHelper.extractErrors(bindingResult));
        }
    }
}
