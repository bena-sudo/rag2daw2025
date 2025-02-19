package org.ieslluissimarro.rag.rag2daw2025.exception;

import java.util.HashMap;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.helper.DataIntegrityViolationAnalyzer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DataIntegrityViolationAnalyzer analyzer;

    public GlobalExceptionHandler(DataIntegrityViolationAnalyzer analyzer){
        this.analyzer=analyzer;
    }

    @ExceptionHandler(EntityIllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityIllegalArgumentException(EntityIllegalArgumentException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
       
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataValidationException(DataValidationException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindingResultException.class)
    public ResponseEntity<BindingResultErrorsResponse> handleBindingResultException(BindingResultException ex) {
        BindingResultErrorsResponse response = new BindingResultErrorsResponse(ex.getErrorCode(), ex.getValidationErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityException(DataIntegrityViolationException ex){
        String detailedMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String errorCode = analyzer.analyzeErrorCode(detailedMessage);
        String useMessage = analyzer.analyzeUserMessage(detailedMessage);

        CustomErrorResponse response = new CustomErrorResponse(errorCode, useMessage);
        response.setDetailedMessage(detailedMessage);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }


}
