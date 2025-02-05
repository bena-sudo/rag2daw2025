package org.ieslluissimarro.rag.rag2daw2025.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentList {


   private Long id;
   private Integer idDocRag;
   //private String dni;
   private String nombreFichero;
   private String estadoDocumento;
   private String extensionDocumento;
   private String contentTypeDocumento;
  
}
