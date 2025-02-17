package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoList {

   private Long id;
   private Integer idDocRag;
   // private String dni;
   private String nombreFichero;
   private String estado;
   private String extensionDocumento;
   private String contentTypeDocumento;

}
