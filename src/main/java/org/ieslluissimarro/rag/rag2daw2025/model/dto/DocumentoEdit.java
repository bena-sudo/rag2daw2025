package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import org.ieslluissimarro.rag.rag2daw2025.model.enums.EstadoDocumento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEdit {

   private Long id;

   private Integer idDocRag;

   // @NotNull(message = "El id del usuario no puede estar vacío")
   // private Long idUsuario;

   private String comentario;

   private EstadoDocumento estadoDocumento;

   private String nombreFichero;

   // Campo para el archivo (no se persiste directamente en la base de datos. Debe pasarse a base64)
   // private MultipartFile multipart;

   // @Size(max = 5, message = "La extensión del documento debe tener como máximo 5 caracteres")
   // private String extensionDocumento;

   // @Size(max = 100, message = "El tipo de contenido debe tener como máximo 100 caracteres")
   // private String contentTypeDocumento;

}
