package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEdit {

   private Long id;
   private Integer idDocRag;
   private String comentario;
   private String estadoDocumento;
   private String nombreFichero;
   private String extensionDocumento;
   private String contentTypeDocumento;

}
