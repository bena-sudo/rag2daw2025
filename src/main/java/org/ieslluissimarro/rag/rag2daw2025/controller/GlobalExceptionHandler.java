package org.ieslluissimarro.rag.rag2daw2025.controller;

import java.util.HashMap;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultErrorsResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultException;
import org.ieslluissimarro.rag.rag2daw2025.exception.CustomErrorResponse;
import org.ieslluissimarro.rag.rag2daw2025.exception.DataValidationException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityAlreadyExistsException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.helper.DataIntegrityViolationAnalyzer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DataIntegrityViolationAnalyzer analyzer;

    public GlobalExceptionHandler(DataIntegrityViolationAnalyzer analyzer) {
        this.analyzer = analyzer;
    }
    
    @ExceptionHandler(EntityIllegalArgumentException.class) //Datos incorrectos en la entidad que producirian inconsistencias
    public ResponseEntity<CustomErrorResponse> handleEntityIllegalArgumentException(EntityIllegalArgumentException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class) //No se ha encontrado la entidad con la clave primaria indicada
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityAlreadyExistsException.class) //Se intenta crear una entidad con una clave primaria que ya existe
    public ResponseEntity<CustomErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataValidationException.class) // Validación incorrecta de un atributo
    public ResponseEntity<CustomErrorResponse> handleDataValidationException(DataValidationException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindingResultException.class) // Errores en las validaciones de BindingResult en la entidad
    public ResponseEntity<BindingResultErrorsResponse> handleBindingResultException(BindingResultException ex) {
        BindingResultErrorsResponse response = new BindingResultErrorsResponse(ex.getErrorCode(), ex.getValidationErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class) //Error en las validaciones @Valid sobre la entidad cuando no hay BindingResult
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        //devolver BAD_REQUEST con la lista de errores
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String detailedMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String errorCode = analyzer.analyzeErrorCode(detailedMessage);
        String userMessage = analyzer.analyzeUserMessage(detailedMessage);

        CustomErrorResponse response = new CustomErrorResponse(errorCode, userMessage);
        response.setDetailedMessage(detailedMessage);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class) //Datos suministrados en la petición HTTP incorrectos
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        CustomErrorResponse response = new CustomErrorResponse("DATA_CONVERSION_ERROR", 
        "Error en el tipo de dato de uno de los atributos suministrados",
        ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(FiltroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse>  handleFiltroException(FiltroException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getDetailedMessage(),ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
