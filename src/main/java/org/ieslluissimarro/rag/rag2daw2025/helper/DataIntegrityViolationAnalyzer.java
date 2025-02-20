package org.ieslluissimarro.rag.rag2daw2025.helper;

import org.springframework.stereotype.Component;

@Component
public class DataIntegrityViolationAnalyzer {

    public static String analyzeErrorCode(String detailedMessage) {
        if (detailedMessage != null && detailedMessage.contains("foreign key")) {
            return "FOREIGN KEY VIOLATION";
        } else if (detailedMessage != null && detailedMessage.contains("unique constraint")) {
            return "UNIQUE CONSTRAINT VIOLATION";
        }
        return "DATA INTEGRITY VIOLATION";
    }

    public static String analyzeUserMessage(String detailedMessage) {
        if (detailedMessage != null && detailedMessage.contains("foreign key")) {
            return "The provided foreign key value does not exist in the related table.";
        } else if (detailedMessage != null && detailedMessage.contains("unique constraint")) {
            return "A record with the same unique value already exists.";
        }
        return "There was a data integrity violation. Please check your input.";
    }
}
