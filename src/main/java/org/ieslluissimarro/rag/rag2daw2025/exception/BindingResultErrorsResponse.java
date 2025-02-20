package org.ieslluissimarro.rag.rag2daw2025.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import lombok.Data;

@Data
public class BindingResultErrorsResponse {
    private String errorCode;
    private Map<String, String> validationErrors;
    private String timestamp;

    public BindingResultErrorsResponse(String errorCode, Map<String, String> validationErrors) {
        this.errorCode = errorCode;
        this.validationErrors = validationErrors;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
