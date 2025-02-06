package org.ieslluissimarro.rag.rag2daw2025.exception.responseHelpers;

public class MultipartProcessingException extends RuntimeException {

   private final String errorCode;
   private final String message;


   public MultipartProcessingException(String errorCode, String message) {
       super(message);
       this.errorCode = errorCode;
       this.message = message;
   }


   public MultipartProcessingException(String errorCode, String message, Throwable cause) {
       super(message, cause);
       this.errorCode = errorCode;
       this.message = message;
   }


   // Getters
   public String getErrorCode() {
       return errorCode;
   }


   @Override
   public String getMessage() {
       return message;
   }

}
