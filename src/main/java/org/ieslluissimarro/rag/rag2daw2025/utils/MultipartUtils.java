package org.ieslluissimarro.rag.rag2daw2025.utils;


import java.io.IOException;
import java.util.Base64;


import org.ieslluissimarro.rag.rag2daw2025.exception.responseHelpers.MultipartProcessingException;
import org.springframework.web.multipart.MultipartFile;


/**
* Utilidad para manejar archivos {@link MultipartFile} en aplicaciones Spring Boot.
*
* <p>Proporciona métodos para convertir archivos multipart en bytes, extraer su extensión
* y codificarlos en Base64.</p>
*
* <p>Si se detectan errores en la manipulación de los archivos, se lanza una excepción
* personalizada {@code MultipartProcessingException}.</p>
*
* @author JoseRa
* @version 1.0
*/
public class MultipartUtils {


   /**
    * Convierte un archivo {@link MultipartFile} en un array de bytes.
    *
    * @param file el archivo multipart a convertir.
    * @return el contenido del archivo en un array de bytes.
    * @throws MultipartProcessingException si el archivo es nulo, está vacío o no se puede leer.
    */
   public static byte[] multipartToBytes(MultipartFile file) {
       if (file == null || file.isEmpty()) {
           throw new MultipartProcessingException("BAD_MULTIPART", "El archivo no puede estar vacío");
       }
       try {
           return file.getBytes(); // Devuelve los bytes del archivo sin codificación
       } catch (IOException e) {
           throw new MultipartProcessingException("FILE_READ_ERROR",
                   "Error al leer el archivo", e);
       }
   }


   /**
    * Obtiene la extensión del archivo a partir del nombre original.
    *
    * @param fichero el archivo multipart del cual obtener la extensión.
    * @return la extensión del archivo (sin el punto inicial).
    * @throws MultipartProcessingException si el archivo es nulo, su nombre es nulo o no tiene extensión válida.
    */
   public static String getExtensionMultipartfile(MultipartFile fichero) {
       if (fichero == null || fichero.getOriginalFilename() == null) {
           throw new MultipartProcessingException("BAD_MULTIPART", "El archivo o su nombre son nulos");
       }
       String original = fichero.getOriginalFilename();
       if (original == null) {
           throw new MultipartProcessingException("INVALID_FILENAME",
                   "El nombre del archivo es nulo");
       }
       int posicionPunto = original.lastIndexOf(".");
       if (posicionPunto < 0 || posicionPunto >= original.length() - 1) {
           throw new MultipartProcessingException("INVALID_FILENAME",
                   "El nombre del archivo no tiene extensión válida");
       }
       return original.substring(posicionPunto + 1);
   }


   /**
    * Convierte un archivo {@link MultipartFile} en una cadena Base64.
    *
    * @param file el archivo multipart a convertir.
    * @return una cadena que representa el contenido del archivo codificado en Base64.
    * @throws MultipartProcessingException si el archivo es nulo, está vacío o no se puede leer.
    */
   public static String multipartToString(MultipartFile file) {
       byte[] bytes = multipartToBytes(file);
       return Base64.getEncoder().encodeToString(bytes); // Codifica en Base64 para su envío como texto
   }
}
