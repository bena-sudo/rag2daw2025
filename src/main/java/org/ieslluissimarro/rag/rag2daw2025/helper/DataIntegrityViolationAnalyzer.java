package org.ieslluissimarro.rag.rag2daw2025.helper;

import org.springframework.stereotype.Component;

@Component
public class DataIntegrityViolationAnalyzer {
    public String analyzeErrorCode(String detailedMessage) {
        if (detailedMessage != null && detailedMessage.contains("foreign key")) {
            return "FOREIGN_KEY_VIOLATION";
        } else if (detailedMessage != null && detailedMessage.contains("unique contraint")) {
            return "UNIQUE_CONTRAINT_VIOLATION";
        }
        return "DATA_INTEGRITY_VIOLATION";
    }

    public String analyzeUserMessage(String detailedMessage) {
        if (detailedMessage != null && detailedMessage.contains("foreign key")) {
            return "The provider foreign key value does not exist in the related table.";
        } else if (detailedMessage != null && detailedMessage.contains("unique contraint")){
            return "A record with the same unique value already exists.";
        }
        return "There was a data integrity violation.Please check your input.";
    }
}
