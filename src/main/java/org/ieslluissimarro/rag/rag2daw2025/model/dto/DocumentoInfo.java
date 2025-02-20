package org.ieslluissimarro.rag.rag2daw2025.model.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.ieslluissimarro.rag.rag2daw2025.model.db.EtiquetaDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoInfo {

   private Long id;
   private Integer idDocRag;
   // private String dni;
   private String nombreFichero;
   private String comentario;
   private String base64Documento;
   private String extensionDocumento;
   private String contentTypeDocumento;
   private String estado;
   private LocalDateTime fechaCreacion;
   private LocalDateTime fechaRevision;
   private Set<EtiquetaDB> etiquetas = new HashSet<>();

}
